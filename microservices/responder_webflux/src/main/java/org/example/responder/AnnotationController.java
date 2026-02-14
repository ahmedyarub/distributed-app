package org.example.responder;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static org.example.responder.Util.utilToLower;

@RestController
public class AnnotationController {
    @GetMapping("/annotation/lower/{param}")
    public Mono<String> annotationConvertLower(@PathVariable String param) {
        return Mono.just(utilToLower(param));
    }
}