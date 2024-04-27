package uz.identityservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.identityservice.entity.UserEntity;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByUsername(String username);
    boolean existsByMobilePhoneAndEnabled(String mobilePhone, boolean enabled);
    Optional<UserEntity> findByMobilePhone(String mobilePhone);
}
