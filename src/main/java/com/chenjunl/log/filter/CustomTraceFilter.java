package com.chenjunl.log.filter;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ReUtil;
import com.chenjunl.log.config.LogTraceStarterConfig;
import com.chenjunl.log.constant.LogStarterConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.core.annotation.Order;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @author chenjunl
 * @description 适用于sleuth3.x.x以上版本
 * @date 2022/12/20
 */
@Slf4j
@Order(2)
public class CustomTraceFilter implements Filter {

    private final LogTraceStarterConfig logTraceStarterConfig;

    private final Tracer tracer;

    public CustomTraceFilter(LogTraceStarterConfig logTraceStarterConfig, Tracer tracer) {
        this.logTraceStarterConfig = logTraceStarterConfig;
        this.tracer = tracer;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletRequest instanceof HttpServletRequest && servletResponse instanceof HttpServletResponse) {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            String requestUri = request.getRequestURI();
            boolean blackFlag = false;
            //先判断黑名单
            if (CollUtil.isNotEmpty(logTraceStarterConfig.getUrlPathBlackRegexList())) {
                blackFlag = logTraceStarterConfig.getUrlPathBlackRegexList().stream().anyMatch(regex -> ReUtil.contains(regex, requestUri));
            }
            //黑名单未命中,再判断白名单,如果命中,添加header
            if (blackFlag) {
                log.trace("路由命中黑名单,响应头不再添加追踪id,请求url:{}", requestUri);
            } else {
                if (CollUtil.isNotEmpty(logTraceStarterConfig.getUrlPathWhiteRegexList())) {
                    if (logTraceStarterConfig.getUrlPathWhiteRegexList().stream().anyMatch(regex -> ReUtil.contains(regex, requestUri))) {
                        addResponseHeader(request, (HttpServletResponse) servletResponse);
                    }
                }
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    /**
     * 对响应添加header
     *
     * @param httpServletResponse
     */
    private void addResponseHeader(HttpServletRequest request, HttpServletResponse httpServletResponse) {
        String traceResponseHeader = LogStarterConstant.TRACE_RESPONSE_HEADER;
        Span currentSpan = tracer.currentSpan();
        if (currentSpan != null) {
            String traceId = currentSpan.context().traceId();
            //如果不为空添加响应头
            if (null != traceId) {
                httpServletResponse.addHeader(traceResponseHeader, traceId);
            }else{
                log.warn("未获取到当前请求tranceId,请求url:{}", request.getRequestURI());
            }
        } else {
            log.warn("未获取到当前请求span,请求url:{}", request.getRequestURI());
        }
    }
}
