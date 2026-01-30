package org.example.messenger.service;

import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.example.messenger.LoginRequest;
import org.example.messenger.UserDto;
import org.example.messenger.config.UserServiceWebClient;
import org.example.messenger.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserServiceWebClient userServiceWebClient;

    public UserDto authenticate(LoginRequest loginRequest){
        return userServiceWebClient.authenticate(loginRequest.username());

    }


//    public void authenticate(LoginRequest loginRequest){
//        UserDto userDto = userServiceClient.findByUsername(loginRequest.username());
//        if(userDto==null){
//            throw new UserNotFoundException("User not found: " + loginRequest.username());
//        }


    }



