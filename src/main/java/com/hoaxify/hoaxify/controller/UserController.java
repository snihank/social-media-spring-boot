package com.hoaxify.hoaxify.controller;

import com.hoaxify.hoaxify.respository.UserRepository;
import com.hoaxify.hoaxify.service.UserService;
import com.hoaxify.hoaxify.user.GenericResponse;
import com.hoaxify.hoaxify.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/api/1.0/users")
    public GenericResponse createUser(@RequestBody User user){
        userService.save(user);
        return new GenericResponse("user saved");
    }
}
