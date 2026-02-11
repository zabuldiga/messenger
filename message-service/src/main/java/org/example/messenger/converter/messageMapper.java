package org.example.messenger.converter;

import org.example.messenger.dto.MessageDto;
import org.example.messenger.entity.Message;
import org.springframework.stereotype.Component;

@Component
public class messageMapper {

    public Message convertToEntity(MessageDto dto) {
        Message message = new Message();
        message.setId(dto.getId());
        message.setSenderId(dto.getSenderId());
        message.setReceiverId(dto.getReceiverId());
        message.setContent(dto.getContent());
        message.setTimestamp(dto.getTimestamp());
        message.setStatus(dto.getStatus());
        return message;
    }

    public MessageDto convertToDto(Message entity) {
        MessageDto dto = new MessageDto();
        dto.setId(entity.getId());
        dto.setSenderId(entity.getSenderId());
        dto.setReceiverId(entity.getReceiverId());
        dto.setContent(entity.getContent());
        dto.setTimestamp(entity.getTimestamp());
        dto.setStatus(entity.getStatus());
        return dto;
    }
}

