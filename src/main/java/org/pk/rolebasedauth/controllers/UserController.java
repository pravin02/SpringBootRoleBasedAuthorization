package org.pk.rolebasedauth.controllers;

import org.pk.rolebasedauth.entities.User;
import org.pk.rolebasedauth.models.UserRole;
import org.pk.rolebasedauth.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;


import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping()
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR')")
    public String getUser(Principal principal) {
        return principal.getName();
    }

    @PostMapping("/join")
    public String registerUser(@RequestBody User user) {
        user.setRoles(UserRole.ROLE_USER.name());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        this.userRepository.save(user);
        return "User Registered successfully";
    }

    @PostMapping("/access/{userId}/{userRole}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR')")
    public String giveAccessToUser(@PathVariable int userId, @PathVariable String userRole, Principal principal) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception(("User not found by " + userId)));
        List<String> activeRoles = getRolesByLoggedInUser(principal);
        if (activeRoles.contains(userRole)) {
            user.setRoles(user.getRoles() + "," + userRole);
            userRepository.save(user);
            return "Hi " + user.getFirstName() + " " + userRole + " assigned to you by " + principal.getName();
        } else {
            return principal.getName() + " you don'y have authority to give role " + userRole + " to " + user.getFirstName();
        }
    }

    private List<String> getRolesByLoggedInUser(Principal principal) {
        String roles = this.getLoggedInUser(principal).getRoles();
        List<String> assignedRoles = Arrays.stream(roles.split(",")).collect(Collectors.toList());
        if (assignedRoles.contains(UserRole.ROLE_ADMIN.name())) {
            return Arrays.stream(UserRole.ADMIN_ACCESS).collect(Collectors.toList());
        }
        if (assignedRoles.contains(UserRole.ROLE_MODERATOR.name())) {
            return Arrays.stream(UserRole.MODERATOR_ACCESS).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }


    private User getLoggedInUser(Principal principal) {
        return userRepository.findByUsername(principal.getName()).get();
    }
}
