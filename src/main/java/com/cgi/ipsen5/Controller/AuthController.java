package com.cgi.ipsen5.Controller;

import com.cgi.ipsen5.Dto.Auth.AuthRequestDTO;
import com.cgi.ipsen5.Dto.Auth.AuthResponseDTO;
import com.cgi.ipsen5.Model.ApiResponse;
import com.cgi.ipsen5.Service.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping(value = "/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping(value = "/login")
    public ApiResponse<Void> login(@RequestBody AuthRequestDTO loginDTO, HttpServletResponse response) {
        authenticationService.login(loginDTO.getUsername(), loginDTO.getPassword(), response);
        return new ApiResponse<>(null, "Login successful", HttpStatus.OK);
    }
}
