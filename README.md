# Castle
Castle的目标是打造一个高性能、高扩展性的java开发平台，完成通用的管理功能。采用了后台管理集中部署，会员业务系统可分离部署等特点，可独立区分后台管理系统、前端展示系统、用户中心系统。开发者可以在此基础上进行扩展，进而使用一个核心就可以开发出各种互联网产品。 

## 特点
* 基于目前最新最热门的java技术
* 采用Java8的语法
* 采用servlet3.1规范的集成机制
* 模块可扩展。直接添加或移除对应模块的jar即可。
* 采用spring javaconfig。 达到零spring配置文件。
* 采用querydsl通用查询
* 包含通用的后台管理
* 可以采用单一服务集成，也可选择地使用第三方服务管理框架（支持dubbo或thrift）。

## 主要用到的第三方功能
* spring-mvc（MVC框架）
* spring-security（安全框架）
* spring-data（通用数据处理）
* querydsl（通用查询框架）
* jackson（json处理框架）
* infinispan（分布式缓存）
* jpa（关系数据库）
* redis（键值数据库）
* mongodb（文档数据库）
* neo4j（图形数据库）
* groovy-template（碎片化模板）
* thymeleaf（前端模板）
* extjs6（js组件框架）
* dubbo（分布式服务框架）
* thrift（异构系统服务调用框架）
