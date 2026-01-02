package org.example.messenger.controller;

import org.example.messenger.UserDto;
import org.example.messenger.config.WebClientConfig;
import org.example.messenger.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("login")
    public ResponseEntity<Map<String, String>> login() {
        System.out.println("Пришел запрос на login: " );
        Map<String, String> response = new HashMap<>();
        response.put("message", "Authentication successful");
        return ResponseEntity.ok(response);
    }

    @PostMapping("register")
    public UserDto register(UserDto userDto) {
        System.out.println("Пришел запрос на регистрацию: " + userDto.getUsername());
        return authService.register(userDto);

    }

}
