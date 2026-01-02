package org.example.messenger.controller;

import org.example.messenger.UserDto;
import org.example.messenger.entity.User;
import org.example.messenger.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users/")
public class UserController {

    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public UserDto register(UserDto userDto) {
        System.out.println("от authservice пришел запрос от : " + userDto.getUsername());
        User user = User.builder().username(userDto.getUsername()).password(userDto.getPassword()).build();
        return userService.register(user);

    }
}
