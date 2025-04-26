package org.example.caller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "responder", url = "http://localhost:8081", path = "/responder")
public interface ResponderClient {
    @GetMapping("/hello")
    HelloClass getHello();
}
