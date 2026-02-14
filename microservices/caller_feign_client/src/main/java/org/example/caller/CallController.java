package org.example.caller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CallController {
    private final FeignResponderClient feignResponderClient;

    public CallController(FeignResponderClient feignResponderClient) {
        this.feignResponderClient = feignResponderClient;
    }

    @GetMapping("/feign_client_annotation/{param}")
    public String feignClientToAnnotation(@PathVariable String param) {
        return feignResponderClient.annotationConvertLower(param);
    }
}