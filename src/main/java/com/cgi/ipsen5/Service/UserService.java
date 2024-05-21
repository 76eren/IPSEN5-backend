package com.cgi.ipsen5.Service;

import com.cgi.ipsen5.Model.PasswordResetToken;
import com.cgi.ipsen5.Model.User;
import com.cgi.ipsen5.Repository.PasswordTokenRepository;
import com.cgi.ipsen5.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public Optional<User> findUserByEmail(String email) {
        Optional<User> foundUser = userRepository.findByUsername(email);
        return foundUser;
    }


}
