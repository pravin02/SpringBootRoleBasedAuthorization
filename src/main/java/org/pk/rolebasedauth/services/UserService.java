package org.pk.rolebasedauth.services;

import org.pk.rolebasedauth.entities.User;
import org.pk.rolebasedauth.models.AppUserDetails;
import org.pk.rolebasedauth.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = this.userRepository.findByUserName(username);

        return user.map(AppUserDetails::new).orElseThrow(() -> new UsernameNotFoundException("User doesn't exists"));
    }
}
