package com.cgi.ipsen5.Controller;

import com.cgi.ipsen5.Dao.UserDao;
import com.cgi.ipsen5.Dto.User.ResetPassword.ChangePasswordDTO;
import com.cgi.ipsen5.Dto.User.ResetPassword.ResetlinkRequestDTO;
import com.cgi.ipsen5.Dto.User.UserFavoriteColleaguesDTO;
import com.cgi.ipsen5.Dto.User.UserEditDTO;
import com.cgi.ipsen5.Dto.User.UserResponseDTO;
import com.cgi.ipsen5.Dto.User.UserStandardLocationDto;
import com.cgi.ipsen5.Exception.UserNotFoundException;
import com.cgi.ipsen5.Exception.UsernameNotFoundException;
import com.cgi.ipsen5.Mapper.UserMapper;
import com.cgi.ipsen5.Model.ApiResponse;
import com.cgi.ipsen5.Model.User;
import com.cgi.ipsen5.Model.Wing;
import com.cgi.ipsen5.Service.FavoriteColleagueService;
import com.cgi.ipsen5.Service.ResetPasswordService;

import com.cgi.ipsen5.Service.StandardLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserDao userDAO;
    private final UserMapper userMapper;
    private final ResetPasswordService resetPasswordService;
    private final FavoriteColleagueService favoriteColleagueService;
    private final StandardLocationService standardLocationService;
    private final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

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

    @GetMapping(path = "/favorite-colleagues")
    public ApiResponse<List<UserFavoriteColleaguesDTO>> getFavoriteColleaguesFromActiveUser() {
        return new ApiResponse<>(this.favoriteColleagueService
                .getFavoriteColleaguesFromEmployee(UUID.fromString(authentication.getName())),
                HttpStatus.OK);
    }

    @PostMapping(path = "/favorite-colleagues")
    public void addFavoriteColleague(@RequestBody UserFavoriteColleaguesDTO favoriteColleaguesDTO) {
        this.favoriteColleagueService.addFavoriteColleague(UUID.fromString(authentication.getName()),
                favoriteColleaguesDTO.getIdOfFavorite());
    }

    @GetMapping(path = "/standard-location")
    public ApiResponse<Wing> getStandardLocation() {
        return new ApiResponse<>(this.standardLocationService
                .getStandardLocation(UUID.fromString(authentication.getName())),
                HttpStatus.OK);
    }

    @PutMapping(path = "/standard-location")
    public void postStandardLocation(@RequestBody UserStandardLocationDto standardLocationDto){
        this.standardLocationService.setStandardLocation(UUID.fromString(authentication.getName()),
                standardLocationDto.getWingId());
    }

    @PostMapping(path = "/reset-password")
    public void requestResetPassword(@RequestBody ResetlinkRequestDTO resetlinkRequestDTO) throws UsernameNotFoundException {
        this.resetPasswordService.requestResetLink(resetlinkRequestDTO);
    }

    @PutMapping(path = "/reset-password")
    public void changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) throws UsernameNotFoundException {
        this.resetPasswordService.changePasswordOfUser(changePasswordDTO);
    }

    @ExceptionHandler
    public ApiResponse<String> handleException(UserNotFoundException e) {
        return new ApiResponse<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

}
