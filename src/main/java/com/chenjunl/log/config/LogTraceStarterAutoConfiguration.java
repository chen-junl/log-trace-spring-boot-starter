package com.chenjunl.log.config;

import brave.baggage.BaggageFields;
import brave.baggage.CorrelationScopeConfig;
import brave.baggage.CorrelationScopeCustomizer;
import com.chenjunl.log.filter.CustomTraceFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author chenjunl
 * @description 自动注入到宿主bean容器中
 * @date 2022/12/22
 */
@Configuration
@EnableConfigurationProperties({LogTraceStarterConfig.class})
public class LogTraceStarterAutoConfiguration {

    @Bean
    @ConditionalOnProperty(value = "log-trace-starter.config.enable", matchIfMissing = true)
    public CustomTraceFilter customTraceFilter(LogTraceStarterConfig logTraceStarterConfig, Tracer tracer) {
        return new CustomTraceFilter(logTraceStarterConfig, tracer);
    }


    /**
     * Sleuth3.x.x 默认只将traceId和spanId添加进入了MDC{@link brave.baggage.CorrelationScopeDecorator.Builder }
     * 如果要将parentId 输出到logback中，需要自定义添加parentId到MDC中
     * Sleuth 为我们提供了自定义扩展接口 CorrelationScopeCustomizer，实现它即可对 builder 进行操作
     * 防止sleuth2.x.x重复设置启动报错,添加配置可以手动开关
     *
     * @return
     */
    @Bean
    @ConditionalOnProperty(value = "log-trace-starter.config.mdc-fields-append-parent-id", matchIfMissing = true)
    public CorrelationScopeCustomizer createCorrelationScopeCustomizer() {
        return builder -> builder
            .add(CorrelationScopeConfig.SingleCorrelationField.create(BaggageFields.PARENT_ID))
            .add(CorrelationScopeConfig.SingleCorrelationField.create(BaggageFields.SAMPLED));
    }
}
