package uz.smsservice.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uz.smsservice.dto.ApiResponse;
import uz.smsservice.dto.MessageRequest;
import uz.smsservice.dto.MessageResponse;
import uz.smsservice.entity.Message;
import uz.smsservice.repository.MessageRepository;

@Service
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    public MessageResponse saveMessage(MessageRequest message) {
        Message save = messageRepository.save(
                new Message(message.getPhoneNumber(),
                            message.getMessage(),
                            message.getTitle(),
                            message.getTitle())
        );
        return  new MessageResponse(save.getCode(), save.getKey(), save.getSendType());
    }

    public ApiResponse getMessage(String message_code) {
        Message message = messageRepository.findByCode(message_code);
        return new ApiResponse(true, "success", new MessageResponse(message.getCode(), message.getKey(), message.getSendType()));
    }

}
