package example.itsec_group_exercise2.controller;

import example.itsec_group_exercise2.service.ConnecterToLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
@RestController
@RequestMapping("/bruteforce")
public class BruteForceController {

    ConnecterToLogin connecterToLogin;

    @Autowired
    public BruteForceController(ConnecterToLogin connecterToLogin) {
        this.connecterToLogin = connecterToLogin;
    }

    @GetMapping
    public String getPassword() {



        System.out.println("connector called");


        return connecterToLogin.tryPasswords("159");
    }

    @GetMapping("/dictionary")
    public String getPassword2() {
        return "password";
    }


    public ClientResponse createClient(ClientRequest request) {

        WebClient webClient = WebClient.builder()
                .baseUrl("http://localhost:9050")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();

        return webClient.post()
                .uri("/userdata")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), ClientRequest.class)
                .retrieve()
                .bodyToMono(ClientResponse.class)
                .timeout(Duration.ofSeconds(3))
                .block();
    }
}
