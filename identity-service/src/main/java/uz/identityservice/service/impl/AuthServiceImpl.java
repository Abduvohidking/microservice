package uz.identityservice.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.identityservice.dto.ApiResponse;
import uz.identityservice.dto.MessageRequest;
import uz.identityservice.dto.RegisterDto;
import uz.identityservice.dto.VerifyDto;
import uz.identityservice.entity.UserEntity;
import uz.identityservice.repository.UserRepository;
import uz.identityservice.service.AuthService;
import uz.identityservice.service.SmsClientApi;
import uz.smsservice.dto.CheckOTPDto;
import uz.smsservice.dto.MessageResponse;
import java.util.Random;

import java.text.MessageFormat;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements UserDetailsService, AuthService {
    private final UserRepository repository;
    private  SmsClientApi smsClientApi;
    private final PasswordEncoder passwordEncoder;
    @Override
    public ApiResponse registerAuth(RegisterDto registerDto) {

        boolean exists = repository.existsByMobilePhoneAndEnabled(registerDto.getPhoneNumber(),true);
        if (exists) return new ApiResponse(false, "User already exists", null);
        Optional<UserEntity> user = repository.findByMobilePhone(registerDto.getPhoneNumber());
        UserEntity savedUser;
        if (user.isPresent()){
            UserEntity userEntity = user.get();
            userEntity.setPassword(passwordEncoder.encode(registerDto.getPassword()));
            savedUser = repository.save(userEntity);
        }else {
            UserEntity userEntity = new UserEntity();
            userEntity.setMobilePhone(registerDto.getPhoneNumber());
            userEntity.setUsername(registerDto.getPhoneNumber());
            userEntity.setPassword(passwordEncoder.encode(registerDto.getPassword()));
            savedUser = repository.save(userEntity);
        }
        Random random = new Random();
        String codeVerifay = String.valueOf(random.nextInt(9999 - 1000 + 1) + 1000);
        MessageRequest messageRequest = new MessageRequest(savedUser.getMobilePhone(), "Tasdiqlash kodi: "+codeVerifay, "xafsizlik", codeVerifay);
        MessageResponse message = smsClientApi.createMessage(messageRequest);


        return new ApiResponse(true,"User created successfully",message);
    }

    @Override
    public ApiResponse verifyAuth(VerifyDto verifyDto) {

        boolean exists = repository.existsByMobilePhoneAndEnabled(verifyDto.getPhoneNumber(), true);
        if (exists) return new ApiResponse(false,"user alredy verified");
        Optional<UserEntity> user = repository.findByMobilePhone(verifyDto.getPhoneNumber());

        CheckOTPDto check = new CheckOTPDto();
        check.setCode(verifyDto.getMessageId());
        check.setKey(verifyDto.getCode());

        boolean b = smsClientApi.checkOTP(check);

        if (!b) return new ApiResponse(false,"message not found or not correct");
        UserEntity userEntity = user.get();
        userEntity.setEnabled(true);
        repository.save(userEntity);

        return new ApiResponse(true,"user verified");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException(
                MessageFormat.format("User %s not found", username)
        ));
    }
}
