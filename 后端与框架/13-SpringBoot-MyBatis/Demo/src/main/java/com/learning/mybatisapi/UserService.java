package com.learning.mybatisapi;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class UserService { private final UserMapper mapper; public UserService(UserMapper mapper){this.mapper=mapper;} public List<User> findAll(){return mapper.findAll();} public User findById(Long id){User user=mapper.findById(id); if(user==null) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"用户不存在"); return user;} public User create(UserRequest req){User user=new User(); user.setName(req.name()); user.setEmail(req.email()); mapper.insert(user); return user;} public User update(Long id,UserRequest req){User user=findById(id); user.setName(req.name()); user.setEmail(req.email()); mapper.update(user); return user;} public void delete(Long id){findById(id); mapper.deleteById(id);} }
