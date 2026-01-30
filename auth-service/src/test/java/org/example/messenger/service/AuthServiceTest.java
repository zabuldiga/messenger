package org.example.messenger.service;

import org.apache.catalina.User;
import org.example.messenger.LoginRequest;
import org.example.messenger.UserDto;
import org.example.messenger.config.UserServiceWebClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.bind.annotation.RequestMapping;

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

        Mockito.when(userServiceWebClient.authenticate("admin")).thenReturn(userDto);

        UserDto result = authService.authenticate(loginRequest);

        Assertions.assertNotNull(result);



    }


}
