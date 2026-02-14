package org.example.caller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
public class CallController {
    private final WebClient webClient;

    public CallController() {
        this.webClient = WebClient.builder().baseUrl("http://localhost:9082/responder_webflux").build();
    }

    @GetMapping("/web_client_annotation/{param}")
    public String webClientToAnnotation(@PathVariable String param) {
        return webClient.get()
                .uri("/annotation/lower/{param}", param)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}