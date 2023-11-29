package com.chenjunl.log.constant;

/**
 * @author chenjunl
 * @description
 * @date 2023/1/17
 */
public class LogStarterConstant {
    /**
     * 日志追踪MDC中的key sleuth版本2.x.x
     */
    public final static String MDC_TRACE_ID_KEY_2 = "X-B3-TraceId";
    /**
     * 日志追踪MDC中的key sleuth版本3.x.x
     */
    public final static String MDC_TRACE_ID_KEY_3 = "traceId";
    /**
     * 日志追踪生成的响应头
     */
    public final static String TRACE_RESPONSE_HEADER = "x-cifnews-trace-id";
}
