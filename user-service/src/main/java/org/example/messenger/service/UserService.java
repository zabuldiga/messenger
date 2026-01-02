package org.example.messenger.service;

import org.example.messenger.UserDto;
import org.example.messenger.converter.UserConverter;
import org.example.messenger.entity.User;
import org.example.messenger.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserConverter userConverter;

    public UserDto register(User user) {
        return userConverter.entityToDto(userRepository.save(user));

    }
}
