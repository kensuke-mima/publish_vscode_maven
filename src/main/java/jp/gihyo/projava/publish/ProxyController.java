package jp.gihyo.projava.publish;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;


@RestController
public class ProxyController {

    @Autowired
    private WebClient.Builder webClientBuilder;

    private final String backendUrl = "http://localhost:8080/resthello";

    @GetMapping("/proxy")
    public Mono<ResponseEntity<String>> proxyGetRequest() {
        return webClientBuilder.build()
            .get()
            .uri(backendUrl)
            .retrieve()
            .toEntity(String.class);
    }

    @PostMapping("/proxy")
    public Mono<ResponseEntity<String>> proxyPostRequest(@RequestBody String body) {
        return webClientBuilder.build()
            .post()
            .uri(backendUrl)
            .bodyValue(body)
            .retrieve()
            .toEntity(String.class);
    }

    //for デバッグ
    @RequestMapping(value="/resthello")
    String hello(){
        return """
                Hello.
                It works!
                現在時刻は%sです。
                """.formatted(LocalDateTime.now());
    }


}
