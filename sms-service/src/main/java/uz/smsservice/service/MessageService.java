package uz.smsservice.service;

import uz.smsservice.dto.ApiResponse;
import uz.smsservice.dto.MessageRequest;
import uz.smsservice.dto.MessageResponse;

public interface MessageService{
    ApiResponse getMessage(String message_code);
    MessageResponse saveMessage(MessageRequest message);
}
