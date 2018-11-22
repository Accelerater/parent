### 1.0 接入 eureka
同Apollodemo2
### 2.0 接入 apollo
同Apollodemo2
### 3.0 接入 openfeign
1 添加openfeign依赖
```Finchley版本
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-openfeign</artifactId>
        </dependency>
```
2 在 **XxxxApplication** 启动类中添加注解 **@EnableFeignClients** 开启feign扫描

3 创建一个借口类 添加注解 **@FeignClient(name = "apollodemo")**

example
```
    @Component
    @FeignClient(name = "apollodemo")
    public interface ApolloDemoService {
        @GetMapping(value = "/port")
        String getPort();
    }
```

4 定义访问控制器内的接口

example
```
    @Autowired
    ApolloDemoService apolloDemoService;

    @GetMapping("getPort")
    public String getPort(){
        return apolloDemoService.getPort();
    }
```

之后步骤同Apollodemo