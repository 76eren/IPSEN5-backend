package com.cgi.ipsen5.Service;


import com.cgi.ipsen5.Dao.UserDao;
import com.cgi.ipsen5.Model.Role;
import com.cgi.ipsen5.Model.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserDao userDAO;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public Optional<String> register(String email, String password, String firstName, String lastName, String phoneNumber) {
        Optional<User> foundUser = userDAO.findByUsername(email);
        if (foundUser.isPresent()) {
            return Optional.empty();
        }

        User user = User.builder()
                .username(email)
                .password(passwordEncoder.encode(password))
                .role(Role.USER)
                .firstName(firstName)
                .lastName(lastName)
                .phoneNumber(phoneNumber)
                .build();

        userDAO.save(user);
        String token = jwtService.generateToken(Map.of("id", user.getId()), user.getId() );
        return Optional.of(token);
    }

    public void login(String username, String password, HttpServletResponse response) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        Optional<User> user = userDAO.findByUsername(username);
        if (user.isPresent()) {
            String token = jwtService.generateToken(Map.of("id", user.get().getId()), user.get().getId());

            Cookie cookie = new Cookie("token", token);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setMaxAge(60 * 60 * 24 * 7);
            response.addCookie(cookie);
        }

    }


    public Cookie logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String token = this.jwtService.generateToken(Map.of("id", authentication.getPrincipal()), (UUID) authentication.getPrincipal());
        jwtService.invalidateToken(token);

        Cookie cookie = new Cookie("token", null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setSecure(false);
        cookie.setMaxAge(0);
        return cookie;
    }
}
