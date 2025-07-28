package org.example.responder;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static org.example.responder.Util.utilToLower;

@RestController
public class StringController {
    @GetMapping("/lower/{param}")
    public String convertLower(@PathVariable String param) {
        return utilToLower(param);
    }
}