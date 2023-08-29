package example.itsec_group_exercise2.config;

import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig implements WebClientCustomizer {

    private final String rootUrl = "http://localhost:9050";

    @Override
    public void customize(WebClient.Builder webClientBuilder) {
        webClientBuilder
                .baseUrl(rootUrl)
                .clientConnector(new ReactorClientHttpConnector(HttpClient.create().secure(sslContextSpec -> {
                    sslContextSpec.sslContext(SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE));
                })));

    }

    @Bean
    public WebClient webClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder.build();
    }

}
