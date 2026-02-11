package org.example.messenger.integration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserServiceWebClient {

    private final WebClient webClient;

    public Long getUserIdByUsername(String username){
        try {
            return webClient.get()
                    .uri("http://localhost:8082/api/v1/users/by-username/{username}",username)
                    .retrieve()
                    .bodyToMono(Long.class)
                    .block();
        }catch (Exception e){
            log.error("Ошибка при запросе userId по username={}: {}", username, e.getMessage());
            return null;

        }

    }
}
