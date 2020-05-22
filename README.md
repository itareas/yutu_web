## 核心配置区

* 系统框架： **SpringBoot+Mybatis+Druid+Thymeleaf+Docker+Vue**

* 系统版本： **V0.6.0**

* 支持数据库：**SqlServer、Oracle、MySql、Redis...**

* 数据库连接池：**Druid**

* 前端模板引擎：Thymeleaf

* 系统接口类型：REST/HttpClient

* 框架安全机制：
>       1.XssAndSqlFilter过滤跨站脚本和sql注入攻击；
>       2.MyFilter过滤非法访问url和用户和实时判断用户登录状态（需要权限系统）
>       3.黑名单机制用来拦截用户恶意访问系统；
>       4.全局异常机制来保护系统出错时候敏感信息泄露；
>       5.必要时可以进行系统配置信息AES全程加密；
 
* 系统特色：
>       1.架构层和业务层配置分离，调整框架不影响业务
>       2.支持多库多数据源同时连接，换库更方便
>       3.多层安全保障机制，保护系统安全 

* 配置区：    
>      configuration：系统配置层,用于配置数据源信息；
>      filter：过滤器，过滤系统url地址；
>      listener：监听器，用于加载起始业务配置文件和黑名单序列化反序列化，加载和监控在线人数和登录退出日志；
>      exception：异常处理层，用于记录全局错误日志；

* 业务区：  
>      controller：控制层,用于前后台数据交互；
>      entity：实体类层；
>      mapper：数据处理层，与数据库交互；
>      service：业务服务层，业务处理层；
>      util：通用工具类；
>      webapi：对外接口层，系统和系统之前交互；

* 系统配置文件：
>      resources/config/*：自定义业务配置文件（关于业务的）；
>      resources/frame: 系统架构说明和数据库；
>      resources/application.properties： 系统核心配置文件，用于配置通用配置；
>      resources/application-dev.properties： 开发环境配置文件，用于开发过程中使用；
>      resources/application-prod.properties： 生产环境配置文件，用于正式版部署使用；
>      resources/log4j.properties：日志配置文件；
>      resources/mybatis-config.xml：mybatis核心配置；
>      resources/readme.md：系统开发手册；       


------------------------------------------------------------------
##开发日志区 

* 2019年12月22日  V0.5.0
>   1.  框架从门户中拆分出来，删除掉无用东西，精简框架
>   2.  对内单点登录已完成，待优化
>   3.  对外单点登录代码已完成，未能跑通

 * 2019年12月29日  V0.5.2
>   1.  对外单点登录 日志 获得菜单完成
>   2.  多开系统冲突问题
>   3.  更新session id存储问题
>   4.  更新myfilter redis判断问题

* 2020年01月02日  V0.6.0
>   1.  干掉拦截器，与过滤器冲突，重复进行拦截,实时和门户心跳验证token实现
>   2.  SessionManage规范化管理，封装注销登录
>   3.  跟门户同步用户/部门功能完成
>   4.  和门户对内对外单点登录已完成
 
* 2020年01月05日  V0.6.1
>   1.  改变接口在配置文件中的存储的模式
>   2.  调整过滤器拦截的配置，由全字符匹配变为模糊字符，例/webapi/
>   3.  sessionUserManger 放弃判断session是否有值

* 2020年01月05日  V0.6.2
>   1.  过滤器白名单方式微调，登录页免拦截

* 2020年01月13日  V0.6.3
>   1.  框架整体细节微调

* 2020年01月14日  V0.6.4
>   1.  调整黑名单写入路径，因打包后无法找到相对路径，已改为物理路径
>   2.  调整系统缓存业务配置文件方式，改成监听器MyServletContextListener中
>   3.  干掉init层，减少反复调用，功能已移植到监听器中

* 2020年05月22日
>   1.  调整工具类名称
