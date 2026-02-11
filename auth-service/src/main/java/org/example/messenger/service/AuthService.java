package org.example.messenger.service;

import lombok.RequiredArgsConstructor;
import org.example.messenger.dto.LoginRequest;
import org.example.messenger.dto.UserDto;
import org.example.messenger.integration.UserServiceWebClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserServiceWebClient userServiceWebClient;

    public UserDto authenticate(LoginRequest loginRequest){
        return userServiceWebClient.getUser(loginRequest.username());

    }


//    public void authenticate(LoginRequest loginRequest){
//        UserDto userDto = userServiceClient.findByUsername(loginRequest.username());
//        if(userDto==null){
//            throw new UserNotFoundException("User not found: " + loginRequest.username());
//        }


    }



