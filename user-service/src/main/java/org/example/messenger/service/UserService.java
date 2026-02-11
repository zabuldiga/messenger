package org.example.messenger.service;

import org.example.messenger.dto.UserDto;
import org.example.messenger.exception.UserNotFoundException;
import org.example.messenger.mapping.UserMapping;
import org.example.messenger.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapping userMapping;

    public UserDto getUser(String username) {
        return userRepository.findByUsername(username)
                .map(userMapping::entityToDto)
                .orElseThrow(() -> new UserNotFoundException("User not found " + username));
    }

    public Long getIdByUsername(String username) {
        return userRepository.findIdByUsername(username);
    }

//    public LoginRequest register(User user) {
//        return userConverter.entityToDto(userRepository.save(user));
//
//    }
}
