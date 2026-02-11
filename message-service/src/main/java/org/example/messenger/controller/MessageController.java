package org.example.messenger.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.messenger.dto.MessageDto;
import org.example.messenger.integration.UserServiceWebClient;
import org.example.messenger.service.MessageProducer;
import org.example.messenger.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/message")
public class MessageController {

    private final MessageProducer messageProducer;
    private final MessageService messageService;

    private final UserServiceWebClient userServiceWebClient;


    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestParam Long receiverId,
                                              @RequestParam String content,
                                              Authentication authentication) {

        String username = (String) authentication.getPrincipal();

        Long senderId = userServiceWebClient.getUserIdByUsername(username);
        if(senderId==null){
            return ResponseEntity.badRequest().body("Не удалось определить senderId по username=" + username);
        }

           messageService.sendMessage(senderId,receiverId,content);

        return ResponseEntity.ok("Сообщение отправлено");


    }

    @GetMapping("/history")
    public ResponseEntity<List<MessageDto>> getChatHistory(@RequestParam Long userId1, @RequestParam Long userId2) {
        log.info("🎯 Controller: запрос истории чата между {} и {}", userId1, userId2);
        List<MessageDto> history = messageService.getChatHistory(userId1, userId2);

        return ResponseEntity.ok(history);


    }

    @GetMapping("/unread")
    public ResponseEntity<List<MessageDto>> getUnreadMessage(@RequestParam Long userId) {
        log.info("🎯 Controller: запрос непрочитанных сообщений для userId={}", userId);

        List<MessageDto> unred = messageService.getUnreadMessage(userId);

        return ResponseEntity.ok(unred);
    }
}
