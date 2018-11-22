package io.daocloud.openfeigndemo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Service
@FeignClient(name = "apollodemo")
public interface ApolloDemoService {
    @GetMapping(value = "/port")
    String getPort();
}
