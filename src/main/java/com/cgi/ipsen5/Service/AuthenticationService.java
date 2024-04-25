package com.cgi.ipsen5.Service;


import com.cgi.ipsen5.Dao.UserDao;
import com.cgi.ipsen5.Model.Role;
import com.cgi.ipsen5.Model.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserDao userDAO;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public Optional<String> register(String username, String password) {
        Optional<User> foundUser = userDAO.findByUsername(username);
        if (foundUser.isPresent()) {
            return Optional.empty();
        }

        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .role(Role.USER)
                .build();

        userDAO.save(user);
        String token = jwtService.generateToken(Map.of("id", user.getId()), user.getId() );
        return Optional.of(token);
    }

    public void login(String username, String password, HttpServletResponse response) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        User user = userDAO.loadUserByUsername(username);
        String token = jwtService.generateToken(Map.of("id", user.getId()), user.getId());

        Cookie cookie = new Cookie("jwt", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 7);
        response.addCookie(cookie);
    }

    public boolean isValidToken(String token) {
        try {
            jwtService.validateToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
