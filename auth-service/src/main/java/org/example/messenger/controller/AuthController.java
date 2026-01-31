package org.example.messenger.controller;

import lombok.RequiredArgsConstructor;
import org.example.messenger.LoginRequest;
import org.example.messenger.UserDto;
import org.example.messenger.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest loginRequest) {
             Authentication authentication =  authenticationManager.authenticate(
                     new UsernamePasswordAuthenticationToken(
                             loginRequest.username(),
                             loginRequest.password()

                     )

             );


        return ResponseEntity.ok(Map.of("message", "Authentication successful"));
    }

//    @PostMapping("register")
//    public LoginRequest register(LoginRequest loginRequest) {
////        System.out.println("Пришел запрос на регистрацию: " + userDto.getUsername());
//        return authService.register(loginRequest);
//
//    }

}
