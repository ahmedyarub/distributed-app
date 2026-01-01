package org.example.caller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CallController {
    final ResponderClient responderClient;

    public CallController(ResponderClient responderClient) {
        this.responderClient = responderClient;
    }

    @GetMapping("/web_call_annotation/{param}")
    public String webCallToAnnotation(@PathVariable String param) {
        return responderClient.annotationConvertLower(param);
    }

    @GetMapping("/web_call_router/{param}")
    public String webCallToRouter(@PathVariable String param) {
        return responderClient.routerConvertLower(param);
    }
}