package com.ecommerce.orderservice.common.utils.log;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.owasp.encoder.Encode;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
public class Slf4jMDCFilter extends OncePerRequestFilter {

    private final String responseHeader;
    private final String mdcTokenKey;
    private final String requestHeader;

    public Slf4jMDCFilter() {
        this.responseHeader = MdcUtil.DEFAULT_RESPONSE_TOKEN_HEADER;
        this.mdcTokenKey = MdcUtil.DEFAULT_MDC_UUID_TOKEN_KEY;
        this.requestHeader = MdcUtil.DEFAULT_REQUEST_TOKEN_HEADER;
    }


    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        try {
            final String token = Encode.forJava(extractToken(request));
            MDC.put(mdcTokenKey, token);
            if (StringUtils.hasText(responseHeader)) {
                response.addHeader(responseHeader, token);
            }
            chain.doFilter(request, response);
        } finally {
            MDC.remove(mdcTokenKey);
        }
    }

    private String extractToken(final HttpServletRequest request) {
        final String token;
        if (StringUtils.hasText(requestHeader) && StringUtils.hasText(request.getHeader(requestHeader))) {
            token = request.getHeader(requestHeader);
        } else {
            token = UUID.randomUUID().toString().replace("-", "");
        }
        return token;
    }

    @Override
    protected boolean isAsyncDispatch(final HttpServletRequest request) {
        return false;
    }

    @Override
    protected boolean shouldNotFilterErrorDispatch() {
        return false;
    }
}
