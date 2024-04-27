package uz.identityservice.service;

import uz.identityservice.dto.ApiResponse;
import uz.identityservice.dto.RegisterDto;

public interface AuthService {
    ApiResponse registerAuth(RegisterDto registerDto);
}
