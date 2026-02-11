package org.example.messenger.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.messenger.dto.MessageDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static org.example.messenger.config.RabbitMQConstants.*;


@Service
@RequiredArgsConstructor
@Slf4j
public class MessageProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendMessage(MessageDto messageDto) {
        log.info("Отправка сообщения в RabbitMQ: от userId={} к userId={}",
                messageDto.getSenderId(), messageDto.getReceiverId());

        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, messageDto);

        log.info("Сообщение добавлено в очередь");
    }

}
