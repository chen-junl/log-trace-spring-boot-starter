package com.chenjunl.log.config;

import cn.hutool.core.collection.CollUtil;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author chenjunl
 * @description
 * @date 2022/12/22
 */
@Data
@ConfigurationProperties(prefix = "log-trace-starter.config")
public class LogTraceStarterConfig {

    /**
     * url路径正则白名单集合 默认为['.*'],拦截所有路由
     */
    private List<String> urlPathWhiteRegexList = CollUtil.newArrayList(".*");
    /**
     * url路径正则黑名单集合 默认为actuator,去除健康检查路由
     */
    private List<String> urlPathBlackRegexList = CollUtil.newArrayList("/actuator/.*");
    /**
     * 响应头添加名称 默认为Log-Trace-Id
     */
    private String traceResponseHeader = "Log-Trace-Id";
    /**
     * 是否手动向mdc中追加parentId 默认为true追加
     */
    private boolean mdcFieldsAppendParentId;
}
