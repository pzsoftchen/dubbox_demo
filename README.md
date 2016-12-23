## dubbox_demo project
> dubbox 项目地址：https://github.com/dangdangdotcom/dubbox.git

### dubbox 源码编译
1. 从github上clone下来dubbox的源代码，然后编译： mvn clean install -Dmaven.test.skip
2. 编译后如果要上传本地仓库则需要在pom.xml 添加distributionManagement属性配置对应的仓库地址(mvn clean install deploy -Dmaven.test.skip)
3. 安装zookeeper，参见zookeeper的官网介绍

PS: 目前公司内部的私库上已是我编译过的最新版本`2.8.4`

### 启动dubbo管理控制台dubbo-admin
1. 从第一步中编译出后的dubbo-admin子项目中找到dubbo-admin-2.8.4.war 放到tomcat服务器的webapps目录下
2. 启动tomcat之前需要先启动zookeeper服务
3. 启动tomcat服务，启动成功后访问：http://localhost:8080/dubbo-admin-2.8.4 (登录密码root/root)

### 编写dubbo服务
> 此处实例采用的是spring注解和dubbo配置结合的方式，这种使用方式也是本次推荐的使用方式，原因有两个：
    1. `dubbo`采用配置文件的方式，可以对当前的服务`无侵入透明使用`，并且兼容spring的注解(`Autowired`)，可以将dubbo服务当做本地服务一样使用
    2. `dubbo`的侧重点则更主要的是服务的`治理和拆分`，这块最主要的方式就是采用属性调节来实现,因此采用配置能够避免可能频繁的属性修改

1. 编写dubbo服务的提供者(发布方)
    dubbo服务的提供者其实和普通的spring服务一样
    定义接口：
    ```java
    public interface IFooService {
        Foo getFoo(String fooName);
        Foo getFoo(String fooName, String barName);
        void insertFoo(Foo foo);
        void updateFoo(Foo foo);
    }
    ```
    
    定义实现：
    ```java
    @Service
    public class FooService implements IFooService {
    
        private Logger LOGGER = LoggerFactory.getLogger(FooService.class);
        private static int CALL_COUNT = 0;
        @Override
        public Foo getFoo(String fooName) {
    
            LOGGER.info("input params, fooName:{}", fooName);
            ++ CALL_COUNT;
            LOGGER.info("call fooName time: {}", CALL_COUNT);
            if (CALL_COUNT == 1){ // 模拟超时的情形
                try {
                    Thread.sleep(3*100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return Foo.builder()
                    .fooName(fooName)
                    .build();
        }
    
        @Override
        public Foo getFoo(String fooName, String barName) {
            LOGGER.info("input params, fooName:{}, barName:{}", fooName, barName);
            return Foo.builder()
                    .fooName(fooName)
                    .barName(barName)
                    .build();
        }
    
        @Override
        public void insertFoo(Foo foo) {
            LOGGER.info("insertFoo foo:{}", foo.toString());
            LOGGER.info("inserFoo success!");
        }
    
        @Override
        public void updateFoo(Foo foo) {
            LOGGER.info("updateFoo foo:{}", foo.toString());
            LOGGER.info("updateFoo success!");
        }
    }
    ```
    **注：此处的Service注解采用依然是Spring的注解**
    
    对外暴露dubbo服务：
    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:dubbo="http://code.alibabatech.com/schema/dubbo"
           xmlns:context="http://www.springframework.org/schema/context"
           xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://code.alibabatech.com/schema/dubbo http://code.alibabatech.com/schema/dubbo/dubbo.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">
    
        <context:component-scan base-package="com.daydao"/>
    
        <!-- 提供方应用信息，用于计算依赖关系 -->
        <dubbo:application name="provider-daydao-yy"  owner="pz.chen" organization="daydao-yy" />
        <!--使用zoo keeper作为服务注册中心-->
        <dubbo:registry address="zookeeper://127.0.0.1:2181" check="false"/>
        <!-- 用dubbo协议在20880端口暴露服务 -->
        <dubbo:protocol name="dubbo" port="20880"/>
        <!--暴露服务-->
        <dubbo:service ref="fooService" interface="com.daydao.dubbox.service.IFooService" owner="pz.chen">
            <dubbo:method name="getFoo" retry="3" timeout="100"/>
        </dubbo:service>
        <bean id="fooService" class="com.daydao.dubbox.service.impl.FooService"/>
    </beans>
    ```
    
2. dubbo服务的消费者(订阅方)
   dubbo服务的消费方其实和普通的spring服务一样
   本地服务接口定义
   ```java
   public interface IBooService {
       void boo(String booName);
   }
   ```
   
   dubbo服务的订阅配置
   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:dubbo="http://code.alibabatech.com/schema/dubbo"
          xmlns:context="http://www.springframework.org/schema/context"
          xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://code.alibabatech.com/schema/dubbo http://code.alibabatech.com/schema/dubbo/dubbo.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">
       <!--spring 注解扫描-->
       <context:component-scan base-package="com.daydao"/>
       <!-- 消费方应用名，用于计算依赖关系，不是匹配条件，不要与提供方一样 -->
       <dubbo:application name="consumer-daydao-xx" owner="pz.chen" organization="daydao-xx"/>
       <!-- 使用zookeeper作为注册中心 -->
       <dubbo:registry address="zookeeper://127.0.0.1:2181" check="false"/>
       <!-- 在当前容器中注册生成远程服务代理，可以和本地bean一样使用fooService -->
       <dubbo:reference id="fooService" interface="com.daydao.dubbox.service.IFooService" owner="pz.chen"/>
   </beans>
   ```
    
   消费方使用dubbo服务
   ```java
   @Service
   public class BooService implements IBooService {
   
       private static final Logger LOGGER = LoggerFactory.getLogger(BooService.class);
   
       @Autowired
       private IFooService fooService; // 引用dubbo服务
       @Autowired
       private ICooService cooService; // 引用本地服务
   
       @Override
       public void boo(String booName) {
           LOGGER.info("boo is called! input param, booName:{}", booName);
           cooService.coo(booName);
           Foo foo = fooService.getFoo(booName);
           LOGGER.info("result foo:{}", foo.toString());
       }
   }
   ```
   **注：此处的Service注解采用依然是Spring的注解**

PS： 通过以上服务的发布和订阅过程，可以看到只需要在配置文件中对dubbo服务就行声明就可以了，而在编码和实现的时候和我们现有的开发方式并无不同

### 关于dubbo使用过程中配置注意事项
#### dubbo的服务提供者或消费者均采用配置文件的方式进行配置
```
    官方推荐配置：
       在Provider上尽量多配置Consumer端属性
       原因如下：
       1. 作服务的提供者，比服务使用方更清楚服务性能参数，如调用的超时时间，合理的重试次数，等等
       2. 在Provider配置后，Consumer不配置则会使用Provider的配置值，即Provider配置可以作为Consumer的缺省值。
          否则，Consumer会使用Consumer端的全局设置，这对于Provider不可控的，并且往往是不合理的。

       PS: 配置的覆盖规则：
            1) 方法级配置别优于接口级别，即小Scope优先
            2) Consumer端配置 优于 Provider配置 优于 全局配置，最后是Dubbo Hard Code的配置值
```
    
#### 在Provider可以配置的Consumer端属性
    
        1. timeout，方法调用超时，单位毫秒
        2. retries，失败重试次数，缺省是2（表示加上第一次调用，会调用3次）
        3. loadbalance，负载均衡算法（有多个Provider时，如何挑选Provider调用），缺省是随机（random）。
           还可以有轮训(roundrobin)、最不活跃优先（leastactive，指从Consumer端并发调用最好的Provider，
           可以减少的反应慢的Provider的调用，因为反应更容易累积并发的调用）
        4. actives，消费者端，最大并发调用限制，即当Consumer对一个服务的并发调用到上限后，新调用会Wait直到超时。
         在方法上配置（dubbo:method ）则并发限制针对方法，在接口上配置（dubbo:service）则并发限制针对服务。
    
> dubbo的服务提供方要特别注意，调用处理的**幂等**操作，由于dubbo服务的调用通常受网络状况的影响较大，尤其在调用失败后（有可能是提供方未收到消费方的调用，也有可能提供方处理成功而消费方未收到提供方的返回等），都有可能会发起重调；这里要特别重复调用对于业务的影响
    
#### 配置上管理信息
```

    目前有负责人信息和组织信息（用于区分站点）。
    有问题时便于的找到服务的负责人，至少写两个人以便备份。
    负责人和组织的信息可以在注册中心的上看到。
```
    
#### 应用配置负责人、组织
```xml
    <dubbo:application name="consumer-daydao-xx" owner="pz.chen" organization="daydao-xx"/>
```
#### service配置负责人
```xml
    <!--暴露服务-->
    <dubbo:service ref="fooService" interface="com.daydao.dubbox.service.IFooService" owner="pz.chen">
        <dubbo:method name="getFoo" retry="3" timeout="100"/>
    </dubbo:service>
```
#### reference配置负责人、分组
```xml
    <!-- 在当前容器中注册生成远程服务代理，可以和本地bean一样使用fooService -->
    <dubbo:reference id="fooService" interface="com.daydao.dubbox.service.IFooService" owner="pz.chen"/>
```

  
  
### dubbox资料链接

    [在Dubbo中开发REST风格的远程调用（RESTful Remoting）](https://dangdangdotcom.github.io/dubbox/rest.html)    
    [在Dubbo中使用高效的Java序列化（Kryo和FST）](https://dangdangdotcom.github.io/dubbox/serialization.html)
    [使用JavaConfig方式配置dubbox](https://dangdangdotcom.github.io/dubbox/java-config.html)
    [Dubbo Jackson序列化使用说明](https://dangdangdotcom.github.io/dubbox/jackson.html)
    [Demo应用简单运行指南](https://dangdangdotcom.github.io/dubbox/demo.html)