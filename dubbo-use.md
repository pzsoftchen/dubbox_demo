### dubbo 服务使用场景
#### 服务提供者的情形
1. 服务的提供者只是作为服务的提供者
2. 服务的提供者只是依赖本地服务
    a. 只是依赖不对外的接口服务
    b. 依赖的也是对外提供dubbo服务的接口
3. 服务的提供者同时依赖dubbo服务和本地服务
4. 服务的提供者依赖dubbo服务即服务的提供方又是消费方

#### 服务消费者的情形
1. 服务的消费者依赖dubbo服务
2. 服务的消费者依赖dubbo服务或本地服务


#### 推荐使用策略
1. dubbo的服务提供者或消费者均采用配置文件的方式进行配置
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
Provider上尽量多配置Consumer端的属性，让Provider实现者一开始就思考Provider服务特点、服务质量的问题。
```xml
<dubbo:service interface="com.alibaba.hello.api.HelloService" version="1.0.0" ref="helloService"
    timeout="300" retry="2" loadbalance="random" actives="0"/>
<dubbo:service interface="com.alibaba.hello.api.WorldService" version="1.0.0" ref="helloService"
    timeout="300" retry="2" loadbalance="random" actives="0" >
    <dubbo:method name="findAllPerson" timeout="10000" retries="9" loadbalance="leastactive" actives="5" />
<dubbo:service/>
```
 在Provider可以配置的Consumer端属性有：

    1. timeout，方法调用超时，单位毫秒
    2. retries，失败重试次数，缺省是2（表示加上第一次调用，会调用3次）
    3. loadbalance，负载均衡算法（有多个Provider时，如何挑选Provider调用），缺省是随机（random）。
       还可以有轮训(roundrobin)、最不活跃优先（leastactive，指从Consumer端并发调用最好的Provider，
       可以减少的反应慢的Provider的调用，因为反应更容易累积并发的调用）
    4. actives，消费者端，最大并发调用限制，即当Consumer对一个服务的并发调用到上限后，新调用会Wait直到超时。
     在方法上配置（dubbo:method ）则并发限制针对方法，在接口上配置（dubbo:service）则并发限制针对服务。

Provider上配置合理的Provider端属性
```xml
<dubbo:protocol threads="200" />
<dubbo:service interface="com.alibaba.hello.api.HelloService" version="1.0.0" ref="helloService"
    executes="200" >
    <dubbo:method name="findAllPerson" executes="50" />
</dubbo:service>
```
Provider上可以配置的Provider端属性有：

    1. threads，服务线程池大小
    2. executes，一个服务提供者并行执行请求上限，即当Provider对一个服务的并发调用到上限后，新调用会Wait（Consumer可能到超时）。
        在方法上配置（dubbo:method ）则并发限制针对方法，在接口上配置（dubbo:service），则并发限制针对服务。

配置上管理信息

    目前有负责人信息和组织信息（用于区分站点）。
    有问题时便于的找到服务的负责人，至少写两个人以便备份。
    负责人和组织的信息可以在注册中心的上看到。
应用配置负责人、组织
```xml
<dubbo:application owner="chen.pz" organization="dayhr" />
```
service配置负责人、分组
```xml
<dubbo:service owner="chen.pz" group="time"/>
```
reference配置负责人、分组
```xml
<dubbo:reference owner="chen.pz" group="time"/>
```
2. 使用spring原生的注解扫描进行服务的注入，将spring原生注解和dubbo注解使用分离开
```
    在所有Service上均采用Spring的Service注解，不再使用dubbo提供的Service注解
    在所有Service中的依赖服务均采用Spring的Autowired注解进行依赖注入，不再使用dubbo提供的Reference注解
    dubbo服务的消费者和提供者均采用xml的方式配置依赖
    注：Spring扫描器对于dubbo服务使用Autowired注解的注入，对dubbo的版本有要求，较早版本的dubbo服务无法支持
        测试中选取了最早的版本2.0.7（此版本无法注入，只能采用get/set注入）和 2.5.3（此版本可行）
```

ps: 测试时如果需要在同一台机器上启动两个以上服务提供者时，只需要注意下服务的端口不同即可，作为服务消费者可以不用了解这些细节，只需要连上同一个注册中心订阅服务即可







