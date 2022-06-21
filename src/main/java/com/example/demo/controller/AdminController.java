package com.example.demo.controller;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.demo.controller.DemoParams.ID;
import static com.example.demo.controller.DemoParams.ROLE_BOX;
import static com.example.demo.controller.DemoPaths.*;

@RestController
@RequestMapping(ADMIN_URL)
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    // start page
    @GetMapping(GET_USERS_URL)
    public String showAllUsers(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = (User) userService.loadUserByUsername(userDetails.getUsername());
        model.addAttribute("newUser", new User());
        model.addAttribute("roleList", userService.getAllRoles());
        model.addAttribute("rolesList", user.getRoles());
        model.addAttribute("userList", userService.getAllUsers());
        return "admin";
    }

    // add new user
    @PostMapping(SAVE_USER_URL)
    public String addUser(@RequestBody User user,
                          @RequestParam(value = ROLE_BOX, required = false) Long[] roleBox) {

        user.setRoles(rolesIdToRoles(roleBox));
        userService.saveUser(user);

        return REDIRECT_URL;
    }


    //update user

    @PutMapping(UPDATE_USER_URL)
    public String updateUser(@RequestBody User user,
                             @RequestParam(value = ROLE_BOX, required = false) Long[] roleBox) {

        user.setRoles(rolesIdToRoles(roleBox));
        userService.updateUser(user);

        return REDIRECT_URL;
    }


    //delete user
    @DeleteMapping(DELETE_USER_URL)
    public String deleteUser(@PathVariable(ID) long id) {
        userService.deleteUserById(id);
        return REDIRECT_URL;
    }

    // package

    Set<Role> rolesIdToRoles(Long[] rolesId) {
        return Arrays.stream(rolesId)
                .map(userService::getRoleById)
                .collect(Collectors.toSet());
    }

}