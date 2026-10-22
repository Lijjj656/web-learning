package com.learning.mysqlapi;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> findAll() { return repository.findAll(); }

    public User findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "用户不存在"));
    }

    public User create(UserRequest request) { return repository.save(new User(request.name(), request.email())); }

    public User update(Long id, UserRequest request) {
        User user = findById(id);
        user.setName(request.name());
        user.setEmail(request.email());
        return repository.save(user);
    }

    public void delete(Long id) { repository.delete(findById(id)); }
}
