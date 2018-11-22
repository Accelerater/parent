### 1.0 接入 eureka
 1 添加 **eureka** 依赖
 ```xml
    <!--eureka-->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    </dependency>
 ``` 
 2 在**application.properties**|**application.yml**文件中配置如下内容
 ```
    spring.application.name=apollodemo
    
    server.port=1234
    eureka.instance.ip-address=true
    eureka.client.serviceUrl.defaultZone = ${EUREKA_URL:http://172.16.53.32:8762/eureka}
 ```
 3 在 **XxxxApplication** 启动类中添加注解 **@EnableEurekaClient**
  

### 2.0 接入 apollo
1 添加 **apollo-client** 依赖
 ```xml
    <!--apollo dependency-->
    <dependency>
        <groupId>com.ctrip.framework.apollo</groupId>
        <artifactId>apollo-client</artifactId>
        <version>1.0.0</version>
    </dependency>
 ``` 
 2 在 **XxxxApplication** 启动类中添加注解 **@EnableApolloConfig(value = "first")**
 value里的值就是配在apollo对应配置组里命名空间的名称(根据实际情况修改)
 
 3 在 **xxx.jar** 启动时加入一下参数
```
-Dapollo.meta=http://172.16.53.35:8080 //指定apollo的地址
-Dapp.id=test    //指定apollo的配置组
-Denv=dev   //指定apollo环境
```
 4 example
 ```bash
 java -jar -Denv=dev \
 -Dapp.id=test \
 -Dapollo.meta=http://172.16.53.35:8080 \
 apollodemo-0.0.1-SNAPSHOT.jar

 ```
### 3.0 接入 skywalking
1 加入 **skywalking-agent** 的启动jar包
 相关文件已经 放入**nexus** 中
2 设置启动执行脚本
```bash
java -javaagent:agent/skywalking-agent.jar -jar \
-Dskywalking.collector.servers=172.16.53.43:10800 \
-Dskywalking.agent.application_code=apollodemo \
$JAVA_OPTS apollodemo-0.0.1-SNAPSHOT.jar
``` 

-Dskywalking.agent.application_code=apollodemo 的值必须和第一步配置中的spring.application.name=apollodemo值相同

### 4.0 生成镜像
使用Dockerfile打镜像
```bash
docker build -t 172.16.52.20/ms_platform/apollodemo:1.0 .
``` 
验证镜像
```bash
docker run  -p 1234:1234 -d  -e \
JAVA_OPTS="-Denv=dev -Dapp.id=test -Dapollo.meta=http://172.16.53.35:8080" \
-e JAVA_OPTS_SW="-Dskywalking.collector.servers=172.16.53.43:10800 \
-Dskywalking.agent.application_code=apollodemo" 172.16.52.20/ms_platform/apollodemo:1.0
```
