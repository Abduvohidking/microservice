package uz.smsservice.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uz.smsservice.dto.ApiResponse;
import uz.smsservice.dto.CheckOTPDto;
import uz.smsservice.dto.MessageRequest;
import uz.smsservice.dto.MessageResponse;
import uz.smsservice.entity.Message;
import uz.smsservice.repository.MessageRepository;
import uz.smsservice.service.MessageService;

@Service
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    public MessageResponse saveMessage(MessageRequest message) {
        Message save = messageRepository.save(
                new Message(message.getPhoneNumber(),
                            message.getMessage(),
                            message.getTitle(),
                            message.getKey())
        );
        return  new MessageResponse(save.getCode());
    }

    @Override
    public boolean otpCheck(CheckOTPDto otp) {

        Message byCode = messageRepository.findByCode(otp.getCode());
        return byCode != null && byCode.getKey().equals(otp.getKey());
    }

    public Message getMessage(String message_code) {
        return messageRepository.findByCode(message_code);
    }

}
