package com.cgi.ipsen5.Controller;

import com.cgi.ipsen5.Dto.Auth.*;
import com.cgi.ipsen5.Dto.User.UserCreateDTO;
import com.cgi.ipsen5.Model.ApiResponse;
import com.cgi.ipsen5.Service.AuthenticationService;
import com.cgi.ipsen5.Service.ResetPasswordService;
import jakarta.security.auth.message.AuthStatus;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;
    private final ResetPasswordService resetPasswordService;

    @PostMapping(value = "/login")
    public ApiResponse<?> login(@RequestBody AuthRequestDTO loginDTO, HttpServletResponse response) {
        authenticationService.login(loginDTO.getUsername(), loginDTO.getPassword(), response);
        return new ApiResponse<>("Login succes", HttpStatus.OK);
    }

    // Voor testing doeleinden
    @PostMapping(value = "/register")
    public ApiResponse<AuthResponseDTO> register(@RequestBody UserCreateDTO userCreateDTO) {
        Optional<String> tokenResponse = authenticationService.register(
                userCreateDTO.getEmail(),
                userCreateDTO.getPassword(),
                userCreateDTO.getFirstName(),
                userCreateDTO.getLastName(),
                userCreateDTO.getPhoneNumber()
        );

        if (tokenResponse.isEmpty()) {
            return new ApiResponse<>("User already exists", HttpStatus.BAD_REQUEST);
        }

        String token = tokenResponse.get();
        return new ApiResponse<>(new AuthResponseDTO(token));
    }

    @GetMapping(value = "/authenticated")
    public ApiResponse<AuthCheckResponseDTO> checkAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated()
                && !authentication.getName().equals("anonymousUser");

        AuthCheckResponseDTO authCheckResponseDTO = AuthCheckResponseDTO
                .builder()
                .isAuthenticated(isAuthenticated)
                .build();

        return new ApiResponse<>(authCheckResponseDTO, HttpStatus.OK);
    }

    // TODO: This doesn't actually invalidate the cookies, it just sends back empty cookies
    @PostMapping("/logout")
    public void logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("token", null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setSecure(false);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

    }

    @GetMapping("/isAdmin")
    public ApiResponse<AdminCheckResponseDTO> isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"));
        return new ApiResponse<>(new AdminCheckResponseDTO(isAdmin), HttpStatus.OK);
    }

    @GetMapping("/{token}/isResetTokenValid")
    public ApiResponse<AuthPasswordTokenDTO> isPasswordTokenValid(@PathVariable UUID token){
        boolean isTokenValid = this.resetPasswordService.isPasswordResetTokenValid(token);
        return new ApiResponse<>(new AuthPasswordTokenDTO(isTokenValid), HttpStatus.OK);
    }

}
