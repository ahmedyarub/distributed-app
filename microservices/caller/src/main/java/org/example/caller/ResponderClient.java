package org.example.caller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "responder", url = "http://localhost:8081", path = "/responder")
public interface ResponderClient {
    @GetMapping("/annotation/lower/{param}")
    String annotationConvertLower(@PathVariable String param);

    @GetMapping("/router/lower/{param}")
    String routerConvertLower(@PathVariable String param);
}
