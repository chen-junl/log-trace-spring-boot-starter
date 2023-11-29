# log-trace-spring-boot-starter

## 集成spring-cloud-starter-sleuth的日志追踪的插件
只要引入maven依赖就认为默认开启  
如果需要关闭将`log-trace-starter.config.enable`设置为false  

设置成功响应头中会有**x-cifnews-trace-id**
### 路由匹配规则
* 可以使用**urlPathBlackRegexList**黑名单策略 排除不需要添加追踪的路由 默认为'/actuator/'
* 可以使用**urlPathWhiteRegexList**白名单策略 指定需要添加追踪的路由 默认为'.*',两种规则优先判断黑名单