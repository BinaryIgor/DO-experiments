package com.igor101.dojavaexperiments.controller;

import com.igor101.dojavaexperiments.model.User;
import com.igor101.dojavaexperiments.model.UserRole;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping
    public List<User> users() {
        return List.of(
                new User(1, "user1", "user1@gmail.com",
                        List.of(UserRole.WORKER)),
                new User(2, "user2", "user2@gmail.com",
                        List.of(UserRole.WORKER, UserRole.APPROVER)));
    }
}
