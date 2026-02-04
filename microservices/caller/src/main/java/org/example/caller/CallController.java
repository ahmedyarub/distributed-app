package org.example.caller;

import org.springframework.boot.restclient.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
public class CallController {
    private final FeignResponderClient feignResponderClient;
    private final RestTemplate restTemplate;
    private final WebClient webClient;

    public CallController(FeignResponderClient feignResponderClient, RestTemplateBuilder restTemplateBuilder) {
        this.feignResponderClient = feignResponderClient;
        this.restTemplate = restTemplateBuilder.build();
        this.webClient = WebClient.builder().baseUrl("http://localhost:8081/responder").build();
    }

    @GetMapping("/feign_client_annotation/{param}")
    public String feignClientToAnnotation(@PathVariable String param) {
        return feignResponderClient.annotationConvertLower(param);
    }

    @GetMapping("/feign_client_router/{param}")
    public String feignClientToRouter(@PathVariable String param) {
        return feignResponderClient.routerConvertLower(param);
    }

    @GetMapping("/rest_template_client_annotation/{param}")
    public String restTemplateClientToAnnotation(@PathVariable String param) {
        return restTemplate.getForObject("http://localhost:8081/responder/annotation/lower/{param}", String.class, param);
    }

    @GetMapping("/rest_template_client_router/{param}")
    public String restTemplateToRouter(@PathVariable String param) {
        return restTemplate.getForObject("http://localhost:8081/responder/router/annotation/lower/{param}", String.class, param);
    }

    @GetMapping("/web_client_annotation/{param}")
    public String webClientToAnnotation(@PathVariable String param) {
        return webClient.get()
                .uri("/responder/annotation/lower/{param}", param)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    @GetMapping("/web_client_router/{param}")
    public String webClientToRouter(@PathVariable String param) {
        return webClient.method(HttpMethod.GET)
                .uri(uriBuilder -> uriBuilder.path("/responder/router/lower/{param}").build(param))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}