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
    1. dubbo采用配置文件的方式，可以对当前的服务无任何的侵入，透明使用，并且兼容spring的注解(Autowired)，可以将dubbo服务当做本地服务一样使用
    2. dubbo的侧重点则更主要的是服务的治理和拆分，这块最主要的方式就是采用属性调节来实现,因此采用配置能够避免可能频繁的属性修改

1. 编写dubbo服务的提供者
    dubbo服务的提供者其实和普通的spring服务一样
    定义接口：
    ```
    public interface IFooService {
        Foo getFoo(String fooName);
        Foo getFoo(String fooName, String barName);
        void insertFoo(Foo foo);
        void updateFoo(Foo foo);
    }
    ```
    
    定义实现：
    ```
    @Service
    public class FooService implements IFooService {
    
        private Logger LOGGER = LoggerFactory.getLogger(FooService.class);
    
        @Override
        public Foo getFoo(String fooName) {
            LOGGER.info("input params, fooName:{}", fooName);
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
    `注：此处的Service注解采用依然是Spring的注解`
    
    对外暴露dubbo服务：
    ```
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
        <dubbo:service ref="fooService" interface="com.daydao.dubbox.service.IFooService" owner="pz.chen"/>
        <bean id="fooService" class="com.daydao.dubbox.service.impl.FooService"/>
    </beans>
    ```
    
    `注： 此处要注意在Provider方的配置上，要更多的配置一些Consumer端的属性`
    