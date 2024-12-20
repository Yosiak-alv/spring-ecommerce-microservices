package com.ecommerce.orderservice.common.utils.log;

import org.slf4j.MDC;

public class MdcUtil {
    public static final String DEFAULT_RESPONSE_TOKEN_HEADER = "trace-id";
    public static final String DEFAULT_MDC_UUID_TOKEN_KEY = "Slf4jMDCFilter.UUID";
    public static final String DEFAULT_REQUEST_TOKEN_HEADER = "trace-id";

    public static String getMdcTraceId() {
        return MDC.get(DEFAULT_MDC_UUID_TOKEN_KEY);
    }
}
