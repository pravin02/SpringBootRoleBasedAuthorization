package org.pk.rolebasedauth.controllers;

import org.pk.rolebasedauth.commons.AppConstants;
import org.pk.rolebasedauth.entities.User;
import org.pk.rolebasedauth.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/join")
    public String registerUser(@RequestBody User user) {
        user.setRoles(AppConstants.DEFAULT_ROLE);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        this.userRepository.save(user);
        return "User Registered successfully";
    }

    @GetMapping("/")
    public String getUser(Principal principal) {
        return principal.getName();
    }


}
