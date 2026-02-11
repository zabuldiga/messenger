package org.example.messenger.service;

import org.example.messenger.dto.LoginRequest;
import org.example.messenger.dto.UserDto;
import org.example.messenger.integration.UserServiceWebClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserServiceWebClient userServiceWebClient;

    @InjectMocks
    private AuthService authService;

    @Test
    void returnTrueWhenUserIsAuthenticate() {
        LoginRequest loginRequest = new LoginRequest("admin", "password");
        UserDto userDto = new UserDto("admin");

        Mockito.when(userServiceWebClient.getUser("admin")).thenReturn(userDto);

        UserDto result = authService.authenticate(loginRequest);

        Assertions.assertNotNull(result);



    }


}
