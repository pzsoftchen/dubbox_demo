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
1. 编写服务的提供者
    