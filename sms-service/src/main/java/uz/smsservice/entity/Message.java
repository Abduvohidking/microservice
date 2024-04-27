package uz.smsservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import uz.smsservice.enums.MessageSendType;

import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sms_message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(name = "created_at", updatable = false,nullable = false)
    private Date CreatedAt = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date UpdatedAt;


    @Column(nullable = false, unique = true)

    private String code;

    @Column(name = "phone_number")
    private String phoneNumber;

    private Date sendDate;

    private String message;

    private String title;

    private String key;

    @Enumerated(value = EnumType.STRING)
    private MessageSendType sendType = MessageSendType.CREATED;

    public Message(String phoneNumber, String message, String title,String key) {
        this.phoneNumber = phoneNumber;
        this.message = message;
        this.title = title;
        this.key = key;
    }
    @PrePersist
    public void prePersist() {
        if (this.code == null || this.code.isEmpty()) {
            this.code = generateCode();
        }
    }

    private String generateCode() {
        // Avtomatik ravishda generatsiya qilish
        return "OB" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }

}
