package com.cgi.ipsen5.Service;


import com.cgi.ipsen5.Dao.UserDao;
import com.cgi.ipsen5.Exception.UserNotFoundException;
import com.cgi.ipsen5.Model.PasswordResetToken;
import com.cgi.ipsen5.Model.User;

import com.cgi.ipsen5.Repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    public Optional<User> findUserByEmail(String email) {
        Optional<User> foundUser = userRepository.findByUsername(email);
        return foundUser;
    }

    public void resetPassword(User user, String newPassword){
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public User getUserFromAuthContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID userId = UUID.fromString(authentication.getName());
        Optional<User> user = this.userDao.findById(userId);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        return user.get();
    }
}
