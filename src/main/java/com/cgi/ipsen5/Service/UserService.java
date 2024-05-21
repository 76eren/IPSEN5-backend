package com.cgi.ipsen5.Service;


import com.cgi.ipsen5.Model.User;

import com.cgi.ipsen5.Repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public Optional<User> findUserByEmail(String email) {
        Optional<User> foundUser = userRepository.findByUsername(email);
        return foundUser;
    }

    public void resetPassword(User user, String newPassword){
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }


}
