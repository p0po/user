## 说明

用spring MVC开发的框架

user-common 是web通用组件。包含拦截器，注解，工具类等通用组件。
user-dao MG自动生成的工程，一般不用干预
user-service
user-controller 会有多个controller，可以按照模块划分，
user-web 通过mvn装配工程需要的controller



# `jar` user-common


|字段|类型|是否必须|描述|默认值|
|---|---|---|---|---|
|from|String|false|登录成功后回跳地址|

