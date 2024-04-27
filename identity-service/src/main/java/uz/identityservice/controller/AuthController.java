package uz.identityservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.identityservice.dto.ApiResponse;
import uz.identityservice.dto.RegisterDto;
import uz.identityservice.service.impl.AuthServiceImpl;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthServiceImpl authService;

    @PostMapping("/register")
    public HttpEntity<?> register(@RequestBody  RegisterDto registerDto){
        ApiResponse apiResponse = authService.registerAuth(registerDto);
        return ResponseEntity.ok((apiResponse));
    }
}
