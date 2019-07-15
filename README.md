## 说明

用spring MVC开发的框架

common 是web通用组件。包含拦截器，注解，工具类等通用组件。

dao 是一个pom工程，里面包含base-model(基于mybatis gen生成),base-dao(基于mybatis gen生成),jade-dao(基于paoding-rose，一般自己写dao时候用)和一个multi-dao(包含base-dao和jade-dao)

service 包含多个service，将来会把api和service实现分开

controller 会有多个controller，可以按照模块划分，

web 通过mvn装配工程需要的controller




# `jar` url


|字段|类型|是否必须|描述|默认值|
|---|---|---|---|---|
|from|String|false||

