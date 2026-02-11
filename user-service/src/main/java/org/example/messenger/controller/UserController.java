package org.example.messenger.controller;

import lombok.RequiredArgsConstructor;
import org.example.messenger.dto.UserDto;
import org.example.messenger.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("/{username}")
    public UserDto getUser(@PathVariable String username){
        return userService.getUser(username);

    }

    @GetMapping("/by-username/{username}")
    public ResponseEntity<Long> getUserIdByUsername(@PathVariable String username) {
        Long id = userService.getIdByUsername(username);
        return ResponseEntity.ok(id);
    }

//    @Autowired
//    private UserService userService;
//    @PostMapping("/register")
//    public LoginRequest register(LoginRequest loginRequest) {
//        System.out.println("от authservice пришел запрос от : " + loginRequest.getUsername());
//        User user = User.builder().username(loginRequest.getUsername()).password(loginRequest.getPassword()).build();
//        return userService.register(user);
//
//    }
}
