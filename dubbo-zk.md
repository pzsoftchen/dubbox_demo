### 使用zookeeper作为dubbo的服务注册中心

    环境：
        系统：windows10
        zk: zookeeper3.4.9
        dubbo: 2.5.3

#### zookeeper 安装
1. 下载地址：http://mirrors.cnnic.cn/apache/zookeeper/stable/
2. 安装：
    由于是测试环境因此采用单机的方式安装，
    找到conf目录并添加zk.cfg文件，并配置以下属性

    ```
        tickTime=2000
        dataDir=/var/lib/zookeeper
        clientPort=2181
    ```
3. 启动：找到bin目录，由于我们当前环境为windows,执行以下命令即可

    ```
        zkServer.cmd
    ```

参考文档地址：http://zookeeper.apache.org/doc/r3.4.9/zookeeperStarted.html
