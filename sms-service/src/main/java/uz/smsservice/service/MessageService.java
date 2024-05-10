package uz.smsservice.service;

import uz.smsservice.dto.ApiResponse;
import uz.smsservice.dto.CheckOTPDto;
import uz.smsservice.dto.MessageRequest;
import uz.smsservice.dto.MessageResponse;
import uz.smsservice.entity.Message;

public interface MessageService{
    Message getMessage(String message_code);
    MessageResponse saveMessage(MessageRequest message);

    boolean otpCheck( CheckOTPDto otp);
}
