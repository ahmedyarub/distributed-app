package org.example.responder;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/hello")
    public HelloClass sayHello() {
        return new HelloClass("value1");
    }
}