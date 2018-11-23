## 日志规范

## 1.0 使用logback记录日志

1 在**src/main/resources**下创建名为**logback-spring.xml**的xml文件

下面我们来看看一个普通的logback-spring.xml例子
```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration  scan="true" scanPeriod="60 seconds" debug="false">
    <contextName>apollo</contextName>
    <!--输出到控制台-->
    <appender name="console" class="ch.qos.logback.core.ConsoleAppender">
        <!-- <filter class="ch.qos.logback.classic.filter.ThresholdFilter">
             <level>ERROR</level>
         </filter>-->
        <encoder>
            <pattern>%d{HH:mm:ss.SSS} %contextName [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>

    <!--输出到文件-->
    <appender name="file" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <!-- 日志路径 可以是绝对路径也可以是相对路径-->
        <file>logs/apollo.log</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <!-- 定义了日志的切分方式 -->
            <fileNamePattern>logs/crn/apollo.%d{yyyy-MM-dd}.%i.log</fileNamePattern>
            <!-- 表示只保留最近30天的日志 -->
            <maxHistory>30</maxHistory>
            <!-- 用来指定日志文件的上限大小，例如设置为1GB的话，那么到了这个值，就会删除旧的日志。-->
            <totalSizeCap>1GB</totalSizeCap>
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <!-- maxFileSize:这是活动文件的大小，默认值是10MB -->
                <maxFileSize>10MB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
        </rollingPolicy>
        <encoder>
            <pattern>%d{HH:mm:ss.SSS} %contextName [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>
    <!-- 用来指定最基础的日志输出级别，只有一个level属性。 -->
    <root level="info">
        <appender-ref ref="console" />
        <appender-ref ref="file" />
    </root>
</configuration>

```

## 2.0 使用log4j2

1 添加依赖
```xml
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter</artifactId>
			<exclusions>
				<exclusion>
					<groupId>org.springframework.boot</groupId>
					<artifactId>spring-boot-starter-logging</artifactId>
				</exclusion>
			</exclusions>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-log4j2</artifactId>
		</dependency>
```

2 在**src/main/resources**下创建名为**log4j2.xml**的xml文件
```xml
<?xml version="1.0" encoding="UTF-8"?>
<Configuration status="WARN" monitorInterval="30">
    <Properties>
        <!-- 日志规范 -->
        <Property name="LOG_PATTERN">%d{yyyy-MM-dd HH:mm:ss.SSS} |-%-5level [%thread] %c [%L] -| %msg%n</Property>
    </Properties>
    <Appenders>
        <Console name="Console" target="SYSTEM_OUT" follow="true">
            <PatternLayout pattern="${LOG_PATTERN}"/>
        </Console>
        <!-- 日志文件保存路径 -->
        <RollingFile name="FileAppender" fileName="logs/log4j2-demo.log"
                     filePattern="logs/log4j2-demo-%d{yyyy-MM-dd}-%i.log">
            <PatternLayout>
                <Pattern>${LOG_PATTERN}</Pattern>
            </PatternLayout>
            <Policies>
                <!-- 按天给日志分类 -->
                <TimeBasedTriggeringPolicy interval="1" />
                <!-- 活动日志大小为10MB -->
                <SizeBasedTriggeringPolicy size="10MB" />
            </Policies>
            <!-- 用来指定同一个文件夹下最多有10个日志文件时开始删除最旧的 -->
            <DefaultRolloverStrategy max="10"/>
        </RollingFile>
    </Appenders>
    <Loggers>
        <Root level="info">
            <AppenderRef ref="Console"/>
            <AppenderRef ref="FileAppender"/>
        </Root>
    </Loggers>
</Configuration>
```

## 3.0通过lombok注解使用日志

1 添加依赖
```xml
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.4</version>
            <scope>provided</scope>
        </dependency>
```
2 给编译器安装lombok插件

3 在需要记日志的类上方添加@Slf4j注解并使用

example
```java
@Slf4j  
public class LogExampleOther {  
    
  public static void main(String... args) {  
    log.error("Something else is wrong here");  
  }  
}  
```
配置文件是**logback-spring.xml** 使用的就是logback
配置文件是**log4j2.xml** 使用的就是log4j2