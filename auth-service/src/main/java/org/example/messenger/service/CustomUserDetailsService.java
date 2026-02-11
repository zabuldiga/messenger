package org.example.messenger.service;

import org.example.messenger.dto.UserDto;
import org.example.messenger.integration.UserServiceWebClient;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserServiceWebClient userServiceWebClient;

    public CustomUserDetailsService(UserServiceWebClient userServiceWebClient) {
        this.userServiceWebClient = userServiceWebClient;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto userDto = userServiceWebClient.getUser(username);
        if (userDto == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }


        return User.builder()
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .authorities("ROLE_USER")
                .build();
    }
}
