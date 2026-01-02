package org.example.messenger.converter;

import org.example.messenger.UserDto;
import org.example.messenger.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    public UserDto entityToDto(User user) {
        return UserDto.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();

    }
}
