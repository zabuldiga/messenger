package org.example.messenger.mapping;

import org.example.messenger.UserDto;
import org.example.messenger.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapping {

    public UserDto entityToDto(User user) {
        return UserDto.builder()
                .username(user.getUsername())
                .build();

    }
}
