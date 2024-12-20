package com.ecommerce.orderservice.service.local.order;

import com.ecommerce.orderservice.common.exception.GenericException;
import com.ecommerce.orderservice.common.utils.date.DateUtils;
import com.ecommerce.orderservice.common.utils.log.MdcUtil;
import com.ecommerce.orderservice.domain.dao.CustomerDao;
import com.ecommerce.orderservice.domain.dao.OrderDao;
import com.ecommerce.orderservice.domain.dto.client.payment.PaymentStatus;
import com.ecommerce.orderservice.domain.dto.client.payment.PaymentStatusRequestDto;
import com.ecommerce.orderservice.domain.model.Customer;
import com.ecommerce.orderservice.domain.model.Order;
import com.ecommerce.orderservice.domain.model.OrderDetail;
import com.ecommerce.orderservice.domain.dto.client.payment.PaymentRequestDto;
import com.ecommerce.orderservice.domain.dto.client.payment.PaymentResponseDto;
import com.ecommerce.orderservice.domain.dto.client.product.ProductResponseDto;
import com.ecommerce.orderservice.domain.dto.local.customer.CustomerResponseDto;
import com.ecommerce.orderservice.domain.dto.local.order.OrderDetailResponseDto;
import com.ecommerce.orderservice.domain.dto.local.order.OrderRequestDto;
import com.ecommerce.orderservice.domain.dto.local.order.OrderResponseDto;
import com.ecommerce.orderservice.domain.dto.local.order.OrderStatus;
import com.ecommerce.orderservice.service.client.payment.PaymentClient;
import com.ecommerce.orderservice.service.client.product.ProductClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderDao orderDao;
    private final CustomerDao customerDao;
    private final ProductClient productClient;
    private final PaymentClient paymentClient;

    @Override
    public List<OrderResponseDto> getCustomerOrders(Long customerId) {
        List<Order> orders = orderDao.findByCustomerId(customerId);

        if (orders.isEmpty()) {
            throw new GenericException(HttpStatus.NOT_FOUND.value(), "getCustomerOrders", "No orders found for this customer", null);
        }
        log.info("Orders found for customer: {}", customerId);
        return orders.stream()
                .map(order -> OrderResponseDto.builder()
                        .id(order.getId())
                        .orderDate(order.getOrderDate())
                        .status(OrderStatus.valueOf(order.getStatus()))
                        .totalAmount(order.getTotalAmount())
                        .orderDetails(order.getOrderDetails().stream()
                                .map(this::getOrderDetailResponseDto)
                                .toList())
                        .build())
                .toList();
    }

    @Override
    public OrderResponseDto updateOrderPaymentStatus(Long orderId, PaymentStatusRequestDto paymentStatusRequestDto) {
        Order order = orderDao.findById(orderId)
                .orElseThrow( () -> new GenericException(HttpStatus.NOT_FOUND.value(), "updateOrderPaymentStatus", "No order data found", null));

        PaymentResponseDto payment = paymentClient.updatePaymentStatus(orderId, paymentStatusRequestDto);

        order.setStatus(paymentStatusRequestDto.getStatus() == PaymentStatus.DECLINED ? OrderStatus.PAYMENT_FAILED.name() : OrderStatus.SUCCESS.name());
        orderDao.save(order);
        log.info("Order payment status updated: {}", order.getReferenceNumber());
        return getOrderResponseDto(order, payment);
    }

    @Override
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) {
        // verify products exist
        List<ProductResponseDto> products = verifyProductsExist(orderRequestDto);

        var client = customerDao.findByDui(orderRequestDto.getClient().getDui())
                .orElseGet(() -> customerDao.save(Customer.builder()
                        .dui(orderRequestDto.getClient().getDui())
                        .email(orderRequestDto.getClient().getEmail())
                        .name(orderRequestDto.getClient().getName())
                        .build()));

        // create order and save
        Order order = saveOrder(orderRequestDto, client, products);
        PaymentResponseDto payment  = null;
        String orderStatus;
        try{
            payment = paymentClient.createPayment(PaymentRequestDto.builder()
                    .orderId(order.getId())
                    .amount(order.getTotalAmount())
                    .paymentMethod(orderRequestDto.getPaymentMethod())
                    .traceId(MdcUtil.getMdcTraceId())
                    .build());
            orderStatus = OrderStatus.SUCCESS.name();
        }catch (Exception e){
            log.error("Error creating payment for order: {}", order.getReferenceNumber());
            orderStatus = OrderStatus.PAYMENT_FAILED.name();
        }

        order.setStatus(orderStatus);
        orderDao.save(order);
        return OrderResponseDto.builder()
                .id(order.getId())
                .orderDate(order.getOrderDate())
                .payment(payment)
                .status(OrderStatus.valueOf(orderStatus))
                .totalAmount(order.getTotalAmount())
                .orderDetails(order.getOrderDetails().stream()
                        .map(this::getOrderDetailResponseDto)
                        .toList())
                .client(CustomerResponseDto.builder()
                        .id(client.getId())
                        .dui(client.getDui())
                        .email(client.getEmail())
                        .name(client.getName())
                        .build())
                .build();
    }

    @Override
    public OrderResponseDto getOrderById(Long id) {
        Order order = orderDao.findById(id)
                .orElseThrow( () -> new GenericException(HttpStatus.NOT_FOUND.value(), "getOrderById", "No order data found", null));

        PaymentResponseDto payment = paymentClient.getPaymentByOrderId(id);

        return getOrderResponseDto(order, payment);
    }

    private OrderResponseDto getOrderResponseDto(Order order, PaymentResponseDto payment) {
        return OrderResponseDto.builder()
                .id(order.getId())
                .orderDate(order.getOrderDate())
                .payment(payment)
                .status(OrderStatus.valueOf(order.getStatus()))
                .totalAmount(order.getTotalAmount())
                .orderDetails(order.getOrderDetails().stream()
                        .map(this::getOrderDetailResponseDto)
                        .toList())
                .client(CustomerResponseDto.builder()
                        .id(order.getCustomer().getId())
                        .dui(order.getCustomer().getDui())
                        .email(order.getCustomer().getEmail())
                        .name(order.getCustomer().getName())
                        .build())
                .build();
    }

    private OrderDetailResponseDto getOrderDetailResponseDto(OrderDetail orderDetail) {
        var product = productClient.getProductById(orderDetail.getProductId());
        return OrderDetailResponseDto.builder()
                .product(ProductResponseDto.builder()
                        .id(orderDetail.getProductId())
                        .title(product.getTitle())
                        .price(product.getPrice())
                        .build())
                .quantity(orderDetail.getQuantity())
                .unitPrice(orderDetail.getUnitPrice())
                .totalPrice(orderDetail.getTotalPrice())
                .build();
    }


    private List<ProductResponseDto> verifyProductsExist(OrderRequestDto orderRequestDto) {
        List<ProductResponseDto> products = new ArrayList<>();
        orderRequestDto.getProducts().forEach(product -> {
            var productResponseDto = productClient.getProductById(product.getId());
            products.add(productResponseDto);
        });
        return products;
    }

    private Order saveOrder(OrderRequestDto orderRequestDto, Customer customer, List<ProductResponseDto> products) {
        List<OrderDetail> orderDetails = new ArrayList<>();

        products.forEach(product -> {
            var quantity = orderRequestDto.getProducts().stream()
                    .filter(p -> p.getId().equals(product.getId()))
                    .findFirst()
                    .orElseThrow()
                    .getQuantity();

            var totalPrice = product.getPrice()
                    .multiply(BigDecimal.valueOf(quantity));

            OrderDetail orderDetail = OrderDetail.builder()
                    .productId(product.getId())
                    .quantity(quantity)
                    .unitPrice(product.getPrice())
                    .totalPrice(totalPrice)
                    .build();
            orderDetails.add(orderDetail);
        });

        // Create order and save
        Order order = Order.builder()
                .customer(customer)
                .referenceNumber(UUID.randomUUID().toString())
                .orderDate(DateUtils.getDateTimeNow())
                .orderDetails(orderDetails)
                .totalAmount(orderDetails.stream()
                        .map(OrderDetail::getTotalPrice)
                        .reduce(BigDecimal.ZERO, BigDecimal::add))
                .build();

        // Set order reference in all details
        order.getOrderDetails().forEach(detail -> detail.setOrder(order));

        Order savedOrder = orderDao.save(order);
        log.info("Order created: {}", order.getReferenceNumber());
        return savedOrder;
    }
}
