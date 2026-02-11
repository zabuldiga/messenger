package org.example.messenger.config;

import org.example.messenger.dto.UserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
@Component
public class UserServiceWebClient {


    private final WebClient webClient;

    public UserServiceWebClient(@Value("${user-service.url}") String userServiceUrl) {
        this.webClient = WebClient.create(userServiceUrl);
    }


    public UserDto getUser(String username){

        return this.webClient
                .get()
                .uri("/{username}", username)
                .retrieve()
                .bodyToMono(UserDto.class)
                .block();
    }
}
