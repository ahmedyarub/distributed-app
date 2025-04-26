package org.example.caller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CallController {
    final ResponderClient responderClient;

    public CallController(ResponderClient responderClient) {
        this.responderClient = responderClient;
    }

    @GetMapping("/call")
    public String callServiceB() {
        return responderClient.getHello().getAttribute2().toLowerCase();
    }
}