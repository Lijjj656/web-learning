package com.learning.userapi;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserService {
    private final ConcurrentHashMap<Long, User> users = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(0);

    public UserService() {
        create(new UserRequest("学习者", "learner@example.com"));
    }

    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    public User findById(Long id) {
        User user = users.get(id);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "用户不存在");
        }
        return user;
    }

    public User create(UserRequest request) {
        long id = idGenerator.incrementAndGet();
        User user = new User(id, request.name(), request.email());
        users.put(id, user);
        return user;
    }

    public User update(Long id, UserRequest request) {
        findById(id);
        User updated = new User(id, request.name(), request.email());
        users.put(id, updated);
        return updated;
    }

    public void delete(Long id) {
        findById(id);
        users.remove(id);
    }
}
