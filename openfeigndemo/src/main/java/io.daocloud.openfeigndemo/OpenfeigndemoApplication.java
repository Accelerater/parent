package io.daocloud.openfeigndemo;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
@EnableFeignClients
//@EnableApolloConfig(value = "first")
public class OpenfeigndemoApplication {
    private final
    ApolloDemoService apolloDemoService;

    @Autowired
    public OpenfeigndemoApplication(ApolloDemoService apolloDemoService) {
        this.apolloDemoService = apolloDemoService;
    }

    @GetMapping("getPort")
    public String getPort(){
        return apolloDemoService.getPort();
    }

    public static void main(String[] args) {
        SpringApplication.run(OpenfeigndemoApplication.class, args);
    }
}
