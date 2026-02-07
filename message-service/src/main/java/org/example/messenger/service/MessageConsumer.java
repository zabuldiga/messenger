package org.example.messenger.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.messenger.MessageDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import static org.example.messenger.config.RabbitMQConstants.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageConsumer {

    private final MessageService messageService;
    @RabbitListener(queues = QUEUE)
    public void receiveMessage(MessageDto messageDto) {
        log.info("📥 Получено сообщение из RabbitMQ:");

        messageService.processMessage(messageDto);


    }
}
