package com.cgi.ipsen5.Controller;

import com.cgi.ipsen5.Dto.Auth.AuthCheckResponseDTO;
import com.cgi.ipsen5.Dto.Auth.AuthRegisterDTO;
import com.cgi.ipsen5.Dto.Auth.AuthRequestDTO;
import com.cgi.ipsen5.Dto.Auth.AuthResponseDTO;
import com.cgi.ipsen5.Model.ApiResponse;
import com.cgi.ipsen5.Service.AuthenticationService;
import jakarta.security.auth.message.AuthStatus;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping(value = "/login")
    public ApiResponse<?> login(@RequestBody AuthRequestDTO loginDTO, HttpServletResponse response) {
        authenticationService.login(loginDTO.getUsername(), loginDTO.getPassword(), response);
        return new ApiResponse<>("Login succes", HttpStatus.OK);
    }

    // Voor testing doeleinden
    @PostMapping(value = "/register")
    public ApiResponse<AuthResponseDTO> register(@RequestBody AuthRegisterDTO loginDTO) {
        Optional<String> tokenResponse = authenticationService.register(loginDTO.getEmail(), loginDTO.getPassword(), loginDTO.getFirstName(), loginDTO.getLastName(), loginDTO.getPhoneNumber());

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


}
