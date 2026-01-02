package org.example.messenger.service;

import org.example.messenger.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class AuthService {
    @Autowired
    private WebClient userServiceWebClient;

    public UserDto register(UserDto userDto) {
        return userServiceWebClient.post()
                .uri("/register")
                .bodyValue(userDto)
                .retrieve()
                .bodyToMono(UserDto.class)
                .block();

    }
}
