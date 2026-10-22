package com.learning.jwtapi;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {
    private final UserMapper mapper;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public AuthService(UserMapper mapper, JwtService jwtService) { this.mapper = mapper; this.jwtService = jwtService; }
    public String register(AuthRequest request) {
        if (mapper.findByEmail(request.email()) != null) throw new ResponseStatusException(HttpStatus.CONFLICT, "邮箱已注册");
        User user = new User(); user.setName(request.name()); user.setEmail(request.email()); user.setPasswordHash(encoder.encode(request.password())); mapper.insert(user); return jwtService.createToken(user);
    }
    public String login(LoginRequest request) {
        User user = mapper.findByEmail(request.email());
        if (user == null || !encoder.matches(request.password(), user.getPasswordHash())) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "邮箱或密码错误");
        return jwtService.createToken(user);
    }
    public User currentUser(String token) {
        String email;
        try {
            email = jwtService.getEmail(token);
        }
        catch (io.jsonwebtoken.JwtException | IllegalArgumentException error) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token 无效或已过期");
        }
        User user = mapper.findByEmail(email);
        if (user == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "用户不存在");
        return user;
    }

    public User currentUserFromAuthorization(String authorization) {
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "缺少 Bearer Token");
        }
        String token = authorization.substring("Bearer ".length()).trim();
        if (token.isBlank()) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "缺少 Bearer Token");
        return currentUser(token);
    }
}
