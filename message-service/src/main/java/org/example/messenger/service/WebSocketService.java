package org.example.messenger.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.messenger.dto.MessageDto;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class WebSocketService {
    private final SimpMessagingTemplate simpMessagingTemplate;

    private final Map<Long, String> onlineUsers = new ConcurrentHashMap<>();

    public void sendMessageToUser(Long userId, MessageDto messageDto) {
        log.info("WebSocket: отправка сообщения пользователю userId={}", userId);

        try {
            simpMessagingTemplate.convertAndSendToUser(
                    userId.toString(),
                    "/queue/message",
                    messageDto
            );
            log.info("Сообщение отправлено через WebSocket");
        } catch (Exception e) {
            log.error("Ошибка отправки WebSocket: {} ", e.getMessage());
        }
    }

    public boolean isUserOnline(Long userId) {
        boolean online = onlineUsers.containsKey(userId);
        log.debug("Проверка онлайн userId={}: {}", userId, online ? "ОНЛАЙН" : "ОФФЛАЙН");
        return online;
    }

    public void registerUser(Long userId, String sessionId) {
        onlineUsers.put(userId, sessionId);
        log.info("Пользователь userId={} подключился (session={})", userId, sessionId);
        log.info(" Всего онлайн: {}", onlineUsers.size());
    }

    public void unregisterUser(Long userId) {
        String sessionId = onlineUsers.remove(userId);
        if (sessionId != null) {
            log.info("Пользователь userId={} отключился (session={})", userId, sessionId);
            log.info("Всего онлайн: {}", onlineUsers.size());
        }
    }


    public int getOnlineCount() {
        return onlineUsers.size();
    }
}
