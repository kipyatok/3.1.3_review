package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import static com.example.demo.controller.DemoParams.ROLES;
import static com.example.demo.controller.DemoParams.USERS;
import static com.example.demo.controller.DemoPaths.BASE_URL;
import static com.example.demo.controller.DemoPaths.GET_USERS_URL;

@RestController
@RequestMapping(BASE_URL)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(GET_USERS_URL)
    public String showAllUsers(Model model, Principal principal) {
        User users = (User) userService.loadUserByUsername(principal.getName());
        model.addAttribute(ROLES, users.getRoles());
        model.addAttribute(USERS, users);
        return USERS;
    }
}
