package example.itsec_group_exercise2.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
@Slf4j
public class ConnecterToLogin {

    private final WebClient webClient;

    @Autowired
    public ConnecterToLogin(WebClient webClient) {
        this.webClient = webClient;
    }

    public String tryPasswords(String password) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("username", "user");
        formData.add("password", password);
        log.info("Trying password: " + password);

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
                .block(Duration.ofSeconds(30));
    }
}
