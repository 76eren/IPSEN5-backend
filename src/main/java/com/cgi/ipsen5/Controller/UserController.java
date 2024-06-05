package com.cgi.ipsen5.Controller;

import com.cgi.ipsen5.Dao.UserDao;
import com.cgi.ipsen5.Dto.User.*;
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

    @GetMapping(path = {"/me"})
    public ApiResponse<UserResponseDTO> getMe() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getPrincipal().toString().isEmpty()){
            return new ApiResponse<>("User not found", HttpStatus.NOT_FOUND);
        }
        return new ApiResponse<>(userMapper.fromEntity(userDAO.findById(UUID.fromString(authentication.getPrincipal().toString())).orElseThrow()));
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
    public ApiResponse<List<UserFavoriteColleagesResponseDTO>> getFavoriteColleaguesFromActiveUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getPrincipal().toString().isEmpty()){
            return new ApiResponse<>("User not found", HttpStatus.NOT_FOUND);
        }
        return new ApiResponse<>(this.favoriteColleagueService
                .getFavoriteColleaguesFromEmployee(UUID.fromString(authentication.getPrincipal().toString())),
                HttpStatus.OK);
    }

    @PostMapping(path = "/favorite-colleagues")
    public void addFavoriteColleague(@RequestBody UserFavoriteColleaguesDTO favoriteColleaguesDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        this.favoriteColleagueService.addFavoriteColleague(UUID.fromString(authentication.getPrincipal().toString()),
                favoriteColleaguesDTO.getId());
    }

    @PutMapping(path = "/favorite-colleagues")
    public void removeColleagueFromFavorites(@RequestBody UserFavoriteColleaguesDTO favoriteColleaguesDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        this.favoriteColleagueService.removeColleagueFromFavorites(UUID.fromString(authentication.getPrincipal().toString()),
                favoriteColleaguesDTO.getId());
    }

    @GetMapping(path = "/standard-location")
    public ApiResponse<Wing> getStandardLocation() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return new ApiResponse<>(this.standardLocationService
                .getStandardLocation(UUID.fromString(authentication.getPrincipal().toString())),
                HttpStatus.OK);
    }

    @PutMapping(path = "/standard-location")
    public void postStandardLocation(@RequestBody UserStandardLocationDto standardLocationDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        this.standardLocationService.setStandardLocation(UUID.fromString(authentication.getPrincipal().toString()),
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
