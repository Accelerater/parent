package io.daocloud.apollodemo;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
@Slf4j
//@EnableApolloConfig(value = "first")
public class ApollodemoApplication {
	@Value("${server.port}")
	private String port;

	@GetMapping(value = "/port")
	public String getPort() {
		log.info("this is info");
		return "this ia apollodemo,port is "+port;
	}

	public static void main(String[] args) {
		SpringApplication.run(ApollodemoApplication.class, args);
	}
}