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
     * 是否开始日志追踪
     */
    private boolean enable = true;
    /**
     * url路径正则白名单集合 默认为['.*'],拦截所有路由
     */
    private List<String> urlPathWhiteRegexList = CollUtil.newArrayList(".*");
    /**
     * url路径正则黑名单集合 默认为actuator,去除健康检查路由
     */
    private List<String> urlPathBlackRegexList = CollUtil.newArrayList("/actuator/.*");
    /**
     * 是否手动向mdc中追加parentId 默认为true追加
     */
    private boolean mdcFieldsAppendParentId;
}
