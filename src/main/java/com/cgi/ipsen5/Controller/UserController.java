package com.cgi.ipsen5.Controller;

import com.cgi.ipsen5.Dao.UserDao;
import com.cgi.ipsen5.Dto.User.UserEditDTO;
import com.cgi.ipsen5.Dto.User.UserResponseDTO;
import com.cgi.ipsen5.Mapper.UserMapper;
import com.cgi.ipsen5.Model.ApiResponse;
import com.cgi.ipsen5.Model.PasswordResetToken;
import com.cgi.ipsen5.Model.User;
import com.cgi.ipsen5.Service.AuthenticationService;
import com.cgi.ipsen5.Service.ResetPasswordService;
import com.cgi.ipsen5.Service.ResetlinkEmailService;
import com.cgi.ipsen5.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    private final UserService userService;
    private final ResetlinkEmailService emailService;
    private final ResetPasswordService resetPasswordService;

    @GetMapping
    @ResponseBody
    public ApiResponse<List<UserResponseDTO>> getUsers() {
        return new ApiResponse<>(userDAO.findAllDto());
    }

    @GetMapping(path = {"/{id}"})
    public ApiResponse<UserResponseDTO> getUserById(@PathVariable UUID id) {
        if (userDAO.findById(id).isEmpty()) {
            return new ApiResponse<>("User not found", HttpStatus.NOT_FOUND);
        } else {
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

    @PostMapping("/resetPassword")
    public ApiResponse<UserResponseDTO> requestResetPassword(HttpServletRequest request, @RequestParam("email") String email) {
        Optional<User> optionalUser = userService.findUserByEmail(email);
        if (!optionalUser.isPresent()) {
            return new ApiResponse<>("User not found", HttpStatus.NOT_FOUND);
        }

        User user = optionalUser.get();
        PasswordResetToken passwordResetToken = resetPasswordService.createPasswordResetTokenForUser(user);
        this.emailService.sendEmail(passwordResetToken.getToken(), user.getUsername());
        return new ApiResponse<>("", HttpStatus.OK);

    }

}
