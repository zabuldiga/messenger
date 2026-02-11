package org.example.messenger.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.messenger.service.MessageService;
import org.example.messenger.service.WebSocketService;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.messaging.Message;


@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {

    private final WebSocketService webSocketService;
    private final MessageService messageService;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        // Обёртка над событием CONNECTED
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = accessor.getSessionId();

        // Достаём оригинальное CONNECT-сообщение
        Message<?> connectMessage =
                (Message<?>) accessor.getHeader(SimpMessageHeaderAccessor.CONNECT_MESSAGE_HEADER);
        if (connectMessage == null) {
            log.warn("CONNECT message not found for sessionId={}", sessionId);
            return;
        }

        // Обёртка над CONNECT, тут есть native headers (Authorization, userId)
        StompHeaderAccessor connectAccessor = StompHeaderAccessor.wrap(connectMessage);

        String userIdHeader = connectAccessor.getFirstNativeHeader("userId");
        if (userIdHeader == null) {
            log.warn("WebSocket CONNECT без userId в заголовках, sessionId={}", sessionId);
            return;
        }

        Long userId = Long.parseLong(userIdHeader);

        log.info("WebSocket CONNECT: userId={}, sessionId={}", userId, sessionId);

        // Регистрируем онлайн
        webSocketService.registerUser(userId, sessionId);

        // Отправляем накопившиеся сообщения
        messageService.sendPendingMessage(userId);
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = accessor.getSessionId();

        Object userIdAttr = accessor.getSessionAttributes().get("userId");
        if (userIdAttr == null) {
            log.warn("WebSocket DISCONNECT без userId в сессии, sessionId={}", sessionId);
            return;
        }

        Long userId;
        if (userIdAttr instanceof Long) {
            userId = (Long) userIdAttr;
        } else {
            userId = Long.parseLong(userIdAttr.toString());
        }

        log.info("WebSocket DISCONNECT: userId={}, sessionId={}", userId, sessionId);

        webSocketService.unregisterUser(userId);
    }
}
