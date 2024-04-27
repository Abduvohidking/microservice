package uz.identityservice.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.identityservice.dto.ApiResponse;
import uz.identityservice.dto.MessageRequest;
import uz.identityservice.dto.RegisterDto;
import uz.identityservice.entity.UserEntity;
import uz.identityservice.repository.UserRepository;
import uz.identityservice.service.AuthService;
import uz.identityservice.service.SmsClientApi;
import uz.smsservice.dto.MessageResponse;

import java.text.MessageFormat;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements UserDetailsService, AuthService {
    private final UserRepository repository;
    private  SmsClientApi smsClientApi;
    @Override
    public ApiResponse registerAuth(RegisterDto registerDto) {
        boolean exists = repository.existsByMobilePhoneAndEnabled(registerDto.getPhoneNumber(),true);
        if (exists) return new ApiResponse(false, "User already exists", null);
        Optional<UserEntity> user = repository.findByMobilePhone(registerDto.getPhoneNumber());
        UserEntity savedUser;
        if (user.isPresent()){
            UserEntity userEntity = user.get();
            userEntity.setPassword(registerDto.getPassword());
            savedUser = repository.save(userEntity);
        }else {
            UserEntity userEntity = new UserEntity();
            userEntity.setMobilePhone(registerDto.getPhoneNumber());
            userEntity.setUsername(registerDto.getPhoneNumber());
            userEntity.setPassword(registerDto.getPassword());
            savedUser = repository.save(userEntity);
        }
        MessageRequest messageRequest = new MessageRequest(savedUser.getMobilePhone(), "Tasdiqlash kodi", "xafsizlik", "1254");
        MessageResponse message = smsClientApi.createMessage(messageRequest);


        return new ApiResponse(true,"User created successfully",message);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException(
                MessageFormat.format("User %s not found", username)
        ));
    }
}
