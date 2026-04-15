#### spring IOC
控制反转

把创建实例的权利交给spring
对象间的耦合程度可以降低。
spring管理情况下，创建一个单例很简单

#### spring AOP
面向切面编程，主要是与业务代码进行区分开，比如事务，日志处理，权限控制。
基于动态代理，JDK代理和Cglib代理


#### aotuwired resource 区别
按类型和按名称先后注入的区别

#### bean注入方式
1. 构造器
2. setter
3. 字段注入

####  bead 的生命周期
实例化->属性赋值->初始化->销毁

##### 初始化
1. 获取spring容器的一些资源->Aware接口
2. 可在对象创建前后执行一些方法

#### 循环依赖
A->B
B->A

通过3级缓存解决
1. MAP bean初始化完成的对象
2. MAP bean实例化，未做属性赋值
3. MAP 主要是使用AOP时，用来获取同一个代理对象

#### spring管理事物的方式
编程式事物

声明式事务（AOP）

#### spring事务的传播行为
7种

主要可分为2类，1类是使用事务，另1类是不使用事务。

- 默认情况下，当前存在事务，则加入事务，当前没有事务，则创建事务。 


#### spring事务的隔离级别
1. 使用数据库的隔离级别
2. 读未提交
3. 读已提交
4. 不可重复度
5. 串行化

可能碰到的问题，脏读，不可重复读，幻读

#### spring事务失效场景
1. 方法访问权限错误，未被public修饰。
2. 同一个类中未使用事务注解的方法调用使用注解的方法
3. 数据库得支持事务
4. 使用事务注解的方法的类，未被spring管理

#### springboot自动装配
1. 启动时通过@EnableAutoConfiguration触发自动装配
2. ImportSelector读取META-INF/spring.factories获取所有自动配置类
3. 按条件注解（如@ConditionalOnClass）判断是否生效
4. 生效的配置类向容器注册Bean

#### spring mvc
DispatcherServlet --> HandlerMapping --> HandlerAdapter --> Handler 
1. 请求先到DispatcherServlet
2. 通过url找HandlerMapping，会返回 目标controller 和 拦截器
3. 再找到HandlerAdapter，通过这个dapter去执行拦截器和目标controller
4. 视图解析器...(json)