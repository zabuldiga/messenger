package org.example.messenger.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.messenger.MessageDto;
import org.example.messenger.MessageStatus;
import org.example.messenger.converter.messageMapper;
import org.example.messenger.entity.Message;
import org.example.messenger.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageService {

    private final MessageProducer messageProducer;
    private final MessageRepository messageRepository;
    private final messageMapper messageMapper;

    public void sendMessage(Long senderId, Long receiverId, String content){
        log.info("💬 MessageService: создание сообщения");
        MessageDto messageDto = new MessageDto();
        messageDto.setSenderId(senderId);
        messageDto.setReceiverId(receiverId);
        messageDto.setContent(content);
        messageDto.setTimestamp(LocalDateTime.now());
        messageDto.setStatus(MessageStatus.SENT);

        messageProducer.sendMessage(messageDto);

    }

    public void processMessage(MessageDto messageDto) {
        log.info("⚙️ MessageService: обработка сообщения");

        Message message = messageMapper.convertToEntity(messageDto);
        Message savedMsg = messageRepository.save(message);
        log.info("💾 Сообщение сохранено в БД с id={}", savedMsg.getId());

        log.info("✅ Сообщение обработано");



    }

    public List<MessageDto> getChatHistory(Long userId1, Long userId2) {
        log.info("📜 Получение истории чата между {} и {}", userId1, userId2);

        List<Message> messages = messageRepository.findChatHistory(userId1, userId2);

        return messages.stream()
                .map(messageMapper::convertToDto)
                .collect(Collectors.toList());
    }

    public List<MessageDto>  getUnreadMessage(Long userId){
        log.info("📬 Получение непрочитанных сообщений для userId={}", userId);
       List<Message> messages = messageRepository.findUnreadMessages(userId,MessageStatus.SENT);

       return messages.stream()
               .map(messageMapper::convertToDto)
               .collect(Collectors.toList());

    }
}
