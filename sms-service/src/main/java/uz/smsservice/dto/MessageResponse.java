package uz.smsservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.smsservice.enums.MessageSendType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse {
    private String message_code;
    private String key;
    private MessageSendType sendType;
}
