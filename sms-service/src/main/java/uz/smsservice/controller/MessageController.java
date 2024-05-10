package uz.smsservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.smsservice.dto.CheckOTPDto;
import uz.smsservice.dto.MessageRequest;
import uz.smsservice.dto.MessageResponse;
import uz.smsservice.entity.Message;
import uz.smsservice.service.impl.MessageServiceImpl;

@RestController
@RequestMapping("/api/sms/")
@RequiredArgsConstructor
public class MessageController {

    private final MessageServiceImpl messageService;

    @PostMapping("/create")
    public MessageResponse createMessage(@RequestBody MessageRequest messageRequest) {
        return messageService.saveMessage(messageRequest);
    }

    @PostMapping("/check")
    public boolean checkOTP(@RequestBody CheckOTPDto otp){
        return messageService.otpCheck(otp);
    }

    @GetMapping("{message_code}")
    public Message getMessage(@PathVariable String message_code) {
        return messageService.getMessage(message_code);
    }
}
