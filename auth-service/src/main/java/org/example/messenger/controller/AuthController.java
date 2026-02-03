package org.example.messenger.controller;

import lombok.RequiredArgsConstructor;
import org.example.messenger.JwtResponse;
import org.example.messenger.LoginRequest;
import org.example.messenger.UserDto;
import org.example.messenger.service.AuthService;
import org.example.messenger.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.username(),
                        loginRequest.password()

                )
        );

        String token = jwtTokenUtil.generationToken(loginRequest.username());


        return ResponseEntity.ok(new JwtResponse(token));
    }

//    @PostMapping("register")
//    public LoginRequest register(LoginRequest loginRequest) {
////        System.out.println("Пришел запрос на регистрацию: " + userDto.getUsername());
//        return authService.register(loginRequest);
//
//    }

}
