#Castle-Platform

> 关于我，欢迎关注   
  博客：[ken的博客](http://ken.whenling.com)

Castle Platform的目标是打造一个高性能、高扩展性的java开发平台，完成通用的管理功能。采用了后台管理集中部署，会员业务系统可分离部署等特点，可独立区分后台管理系统、前端展示系统、用户中心系统。开发者可以在此基础上进行扩展，进而使用一个核心就可以开发出各种互联网产品。 

####示例
- 初始学习与建立工程：[castle-example](https://github.com/xiangxik/castle-example)
- 分布式实例：[移动城堡](http://mdm.whenling.com)
- 一站式实例：[床垫材料](http://www.gzcdcl.com)

###特性
- 基于目前最新最热门的java技术
- 采用Java8的语法
- 采用servlet3.1规范的集成机制
- 模块可扩展。直接添加或移除对应模块的jar即可。
- 采用spring javaconfig。 达到零spring配置文件。
- 支持多种数据操作混用
- 采用querydsl通用查询
- 包含通用的后台管理
- 可以采用单一服务集成，也可选择地使用第三方服务管理框架（支持dubbo或thrift）。
- 支持javaconfig和kryo序列化的dubbo
- 支持国际化

###原理说明
采用Dubbo等第三方RMI框架，把业务处理放置于多个Provider， Web服务作为Consumer，对Provider进行调用，从而达到分布式部署。<br/>
![Alt framework](http://ken.whenling.com/img/castle/frame.jpg)

###集成的第三方框架
- spring-mvc（MVC框架）
- spring-security（安全框架）
- spring-data（通用数据处理）
- querydsl（通用查询框架）
- jackson（json处理框架）
- infinispan（分布式缓存）
- jpa（关系数据库）
- redis（键值数据库）
- mongodb（文档数据库）
- neo4j（图形数据库）
- groovy-template（碎片化模板）
- thymeleaf（前端模板）
- extjs6（js组件框架）
- dubbo（分布式服务框架）
- thrift（异构系统服务调用框架）

###使用方法
[http://ken.whenling.com/2016/04/24/castle/](http://ken.whenling.com/2016/04/24/castle/)

###维护计划
- bootstrap管理后台
- 通用权限管理
- 手机app集成
- spring webservice
- 文件集中式存储
- 分布式计算

## License
[Apache License Version 2.0](https://github.com/xiangxik/castle-platform/blob/master/LICENSE)
