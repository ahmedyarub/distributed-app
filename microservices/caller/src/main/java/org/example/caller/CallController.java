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

    @GetMapping("/web_call/{param}")
    public String webCall(@PathVariable String param) {
        return responderClient.convertLower(param);
    }
}