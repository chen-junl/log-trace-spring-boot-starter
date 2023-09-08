# log-trace-spring-boot-starter
集合spring-cloud-starter-sleuth的日志追踪的插件
只有引入maven依赖就认为默认开启
可以使用**urlPathBlackRegexList**黑名单策略 排除不需要添加追踪的路由 默认为'/actuator/'
可以使用**urlPathWhiteRegexList**白名单策略 指定需要添加追踪的路由 默认为'.*',两种规则优先判断黑名单
可以使用**traceResponseHeader**声明在请求中添加的header 默认为**Log-Trace-Id**
