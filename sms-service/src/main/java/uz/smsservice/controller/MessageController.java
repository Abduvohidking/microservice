package uz.smsservice.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import uz.smsservice.dto.ApiResponse;
import uz.smsservice.dto.MessageRequest;
import uz.smsservice.dto.MessageResponse;
import uz.smsservice.service.MessageServiceImpl;

@RestController
@RequestMapping("/api/sms/")
@RequiredArgsConstructor
public class MessageController {

    private final MessageServiceImpl messageService;

    @PostMapping("/create")
    public HttpEntity<?> createMessage(@RequestBody MessageRequest messageRequest) {
        MessageResponse apiResponse = messageService.saveMessage(messageRequest);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("{message_code}")
    public HttpEntity<?> getMessage(@PathVariable String message_code) {
        ApiResponse apiResponse = messageService.getMessage(message_code);
        return ResponseEntity.ok(apiResponse);
    }
}
