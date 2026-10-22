package com.learning.jwtapi;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController @RequestMapping("/api/auth")
public class AuthController {
    private final AuthService service;
    public AuthController(AuthService service) { this.service = service; }
    @PostMapping("/register") public Map<String, String> register(@Valid @RequestBody AuthRequest request) { return Map.of("token", service.register(request)); }
    @PostMapping("/login") public Map<String, String> login(@Valid @RequestBody LoginRequest request) { return Map.of("token", service.login(request)); }
    @GetMapping("/me") public UserProfile me(@RequestHeader(value = "Authorization", required = false) String authorization) { return UserProfile.from(service.currentUserFromAuthorization(authorization)); }
}
