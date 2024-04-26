package com.cgi.ipsen5.Controller;

import com.cgi.ipsen5.Dao.UserDao;
import com.cgi.ipsen5.Dto.Auth.AuthRegisterDTO;
import com.cgi.ipsen5.Dto.Auth.AuthResponseDTO;
import com.cgi.ipsen5.Dto.User.UserEditDTO;
import com.cgi.ipsen5.Dto.User.UserResponseDTO;
import com.cgi.ipsen5.Mapper.UserMapper;
import com.cgi.ipsen5.Model.ApiResponse;
import com.cgi.ipsen5.Model.User;
import com.cgi.ipsen5.Service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserDao userDAO;
    private final UserMapper userMapper;
    private final AuthenticationService authenticationService;

    @GetMapping
    @ResponseBody
    public ApiResponse<List<UserResponseDTO>> getUsers() {
        return new ApiResponse<>(userDAO.findAllDto());
    }

    @PostMapping(value = "/register")
    public ApiResponse<AuthResponseDTO> register(@RequestBody AuthRegisterDTO loginDTO) {
        Optional<String> tokenResponse = authenticationService.register(loginDTO.getUsername(), loginDTO.getPassword());

        if (tokenResponse.isEmpty()) {
            return new ApiResponse<>("User already exists", HttpStatus.BAD_REQUEST);
        }

        String token = tokenResponse.get();
        return new ApiResponse<>(new AuthResponseDTO(token));
    }

    @GetMapping(path = {"/{id}"})
    public ApiResponse<UserResponseDTO> getUserById(@PathVariable UUID id) {
        if (userDAO.findById(id).isEmpty()) {
            return new ApiResponse<>("User not found", HttpStatus.NOT_FOUND);
        }
        else {
            UserResponseDTO res = userMapper.fromEntity(userDAO.findById(id).orElseThrow());
            return new ApiResponse<>(res);
        }
    }

    @PutMapping(path = {"/{id}/edit"})
    public ApiResponse<UserResponseDTO> editUser(
            @PathVariable("id") UUID id,
            @RequestBody UserEditDTO userEditDTO
    ) {
        User updatedUser = userDAO.updateUser(id, userEditDTO);
        if (updatedUser == null) {
            return new ApiResponse<>("User not found", HttpStatus.NOT_FOUND);
        }

        return new ApiResponse<>(userMapper.fromEntity(updatedUser));
    }

    @GetMapping(path = {"/test"})
    public ApiResponse<String> test(@CookieValue(name = "token", defaultValue = "No cookie found") String token) {
        System.out.println("Cookie value is: " + token);
        return new ApiResponse<>("Test");
    }

}
