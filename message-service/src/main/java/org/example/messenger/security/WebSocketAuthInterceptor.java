package org.example.messenger.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.messenger.JwtTokenUtil;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketAuthInterceptor implements ChannelInterceptor {
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
            String authToken = accessor.getFirstNativeHeader("Authorization");
            if (authToken != null && authToken.startsWith("Bearer ")) {
                String token = authToken.substring(7);
                try {
                    String username = jwtTokenUtil.extractUsername(token);

                    if (jwtTokenUtil.validateToken(token, username)) {
                        accessor.getSessionAttributes().put("username", username);
                        String userIdHeader = accessor.getFirstNativeHeader("userId");
                        if (userIdHeader != null) {
                            accessor.getSessionAttributes().put("userId", Long.parseLong(userIdHeader));
                        }
                    } else {
                        log.info("Невалидный токен!");
                        return null;

                    }
                } catch (Exception e) {
                    log.error("Ошибка парсинга JWT; {}", e.getMessage());
                    return null;

                }

            }

        }
        return message;
    }
}
