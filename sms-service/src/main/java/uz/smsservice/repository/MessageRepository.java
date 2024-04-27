package uz.smsservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.smsservice.entity.Message;

public interface MessageRepository extends JpaRepository<Message,Long> {
    Message findByCode(String code);
}
