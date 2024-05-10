package uz.identityservice.service;

import uz.identityservice.dto.ApiResponse;
import uz.identityservice.dto.RegisterDto;
import uz.identityservice.dto.VerifyDto;

public interface AuthService {
    ApiResponse registerAuth(RegisterDto registerDto);
    ApiResponse verifyAuth(VerifyDto verifyDto);
}
