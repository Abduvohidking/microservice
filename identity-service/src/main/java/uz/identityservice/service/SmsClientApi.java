package uz.identityservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import uz.identityservice.dto.MessageRequest;
import uz.identityservice.dto.SmsResponseDto;
import uz.smsservice.dto.CheckOTPDto;
import uz.smsservice.dto.MessageResponse;


@FeignClient(name = "SMS-SERVICE")
public interface SmsClientApi {

    @GetMapping("api/sms/{message_code}")
    SmsResponseDto getSms(@PathVariable("message_code") String message_code);

    @PostMapping("api/sms/create")
    MessageResponse createMessage(@RequestBody MessageRequest message);

    @PostMapping("api/sms/check")
    boolean checkOTP(@RequestBody CheckOTPDto otp);
}
