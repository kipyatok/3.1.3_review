package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/")
public class UserController {


    private UserDetailServiceImpl userService;

    @Autowired
    public UserController(UserDetailServiceImpl userService) {
        this.userService = userService;
    }

    // начальная страница
    @GetMapping({"/", "/index"})
    public String indexPage() {
        return "index";
    }

    @GetMapping({"authorized"})
    public String authorizedPage() {
        return "authorized";
    }

    @RequestMapping("/user")
    public String showAllUsers(Model model, Principal principal) {
        User user = (User) userService.loadUserByUsername(principal.getName());
        model.addAttribute("currentUserRoleList", user.getRoles());
        model.addAttribute("currentUser", user);
        System.out.println("showAllUsers/allUsers " + user.getRoles().toString());
        return "user";
    }


}
