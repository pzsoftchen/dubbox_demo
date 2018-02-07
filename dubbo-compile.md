
> dubbo编译时的问题总结，最近准备研究一下dubbo的源码，准备从最旧的版本开始，可能由于版本过早有些依赖发生了变化，有些包可能也找不到了，因此编译时碰到不少问题。不过还算顺利总的来说通过以下方法都解决了：

###修改maven的配置文件setting.xml    
``` xml
    找到mirrors属性，添加如下配置：
    <mirror>
       <id>nexus-aliyun</id>
       <mirrorOf>*</mirrorOf>
       <name>Nexus aliyun</name>
       <url>http://maven.aliyun.com/nexus/content/groups/public</url>
    </mirror> 
    <mirror>
        <id>lvu.cn</id>
        <name>lvu.cn</name>
        <url>http://lvu.cn/nexus/content/groups/public</url>
        <mirrorOf>*</mirrorOf>
    </mirror>
```
###解决 opensesame 依赖问题
```
    下载：	
    https://github.com/alibaba/opensesame.git
    https://github.com/alibaba/opensesame
    注意此版本为：2.0
    安装：
    mvn clean install -Dmaven.test.skip
    找到dubbo项目的pom文件中关于opensesame的依赖修改对应的版本信息，如下：
    <groupId>com.alibaba</groupId>
    <artifactId>opensesame</artifactId>
    <version>2.0</version>
```	
###hessian-lite 依赖
```	
    下载:
    https://github.com/ROCK-SOLID-CN/hessian-lite.git
    https://github.com/ROCK-SOLID-CN/hessian-lite
    注意版本问题
    安装：
    mvn clean install -Dmaven.test.skip
    找到dubbo项目的pom文件中关于opensesame的依赖修改对应的版本信息，如下：
    <groupId>cn.rock-solid</groupId>
    <artifactId>hessian-lite</artifactId>
    <version>3.2.1-fixed-2</version>
```
### fastjson 版本问题
```
    将dubbo项目中pom依赖文件中的版本号 fastjson_version 调整至最新版本 1.2.9;
    好像1.1.8的版本在以上设置的仓库中没找到，因此也导致了一些编译时的报错
```

### 管理控制台安装dubbo-admin2.5.3编译后部署报错的问题
主要是由于运行环境为jdk1.8所致
解决办法：
1、webx的依赖改为3.1.6版；
```xml
 <dependency>
        <groupId>com.alibaba.citrus</groupId>
        <artifactId>citrus-webx-all</artifactId>
        <version>3.1.6</version>
 </dependency>
```
2. 添加velocity的依赖，我用了1.7
```xml
 <dependency>
        <groupId>org.apache.velocity</groupId>
        <artifactId>velocity</artifactId>
        <version>1.7</version>
  </dependency>
```
3. 对依赖项dubbo添加exclusion，避免引入旧spring
```xml
<dependency>
        <groupId>com.alibaba</groupId>
        <artifactId>dubbo</artifactId>
        <version>${project.parent.version}</version>
        <exclusions>
            <exclusion>
                <groupId>org.springframework</groupId>
                <artifactId>spring</artifactId>
            </exclusion>
        </exclusions>
 </dependency>
```
4. webx已有spring 3以上的依赖，因此注释掉dubbo-admin里面的spring依赖
```xml
 <!--<dependency>-->
        <!--<groupId>org.springframework</groupId>-->
        <!--<artifactId>spring</artifactId>-->
    <!--</dependency>-->
```
确定war包解压后lib目录没有spring 3 以下的依赖就行。然后运行正常了。
详见：https://github.com/alibaba/dubbo/issues/50
5. 安装成功后
```
    访问地址url：http://localhost:8080
    账号：用户:root,密码:root 或 用户:guest,密码:guest
```
> 总结：
	通过以上方法，亲测可以成功编译github上能够下载的最早的dubbo-2.0.7的版本，我又尝试了2.2.0, 2.3.0, 2.4.0版本均能顺利编译,所以相信别的版本应该也没问题
