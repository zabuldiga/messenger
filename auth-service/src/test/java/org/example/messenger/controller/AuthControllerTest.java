package org.example.messenger.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.messenger.LoginRequest;
import org.example.messenger.UserDto;
import org.example.messenger.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AuthService authService;

//   @MockitoBean
//   private UserServiceClient userServiceClient;

    @Test
    void shouldReturn200WhenUserExist() throws Exception {
        LoginRequest loginRequest = new LoginRequest("admin", "password");
        UserDto userDto = new UserDto("admin");
         when(authService.authenticate(any(LoginRequest.class))).thenReturn(userDto);

         mockMvc.perform(post("/api/v1/auth/login")
                 .contentType(MediaType.APPLICATION_JSON)
                 .content(objectMapper.writeValueAsBytes(loginRequest)))
                 .andExpect(status().is2xxSuccessful())
                 .andExpect(jsonPath("$.message").value("Authentication successful"));



    }
//    @Test
//    void shouldReturn401WhenUserNotFound() throws Exception{
//
//        LoginRequest loginRequest = new LoginRequest("ivan", "123456");
//
//        doThrow(new UserNotFoundException("User not found"))
//                .when(authService).authenticate(any(LoginRequest.class));
//
//        mockMvc.perform(post("/api/v1/auth/login")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsBytes(loginRequest)))
//                .andExpect(status().isUnauthorized());
//
//
//
//    }
}
