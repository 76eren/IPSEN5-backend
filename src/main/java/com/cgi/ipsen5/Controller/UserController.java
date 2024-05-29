package com.cgi.ipsen5.Controller;

import com.cgi.ipsen5.Dao.UserDao;
import com.cgi.ipsen5.Dto.User.ResetPassword.ChangePasswordDTO;
import com.cgi.ipsen5.Dto.User.ResetPassword.ResetlinkRequestDTO;
import com.cgi.ipsen5.Dto.User.UserAddFavoriteColleaguesDTO;
import com.cgi.ipsen5.Dto.User.UserEditDTO;
import com.cgi.ipsen5.Dto.User.UserResponseDTO;
import com.cgi.ipsen5.Exception.UserNotFoundException;
import com.cgi.ipsen5.Exception.UsernameNotFoundException;
import com.cgi.ipsen5.Mapper.UserMapper;
import com.cgi.ipsen5.Model.ApiResponse;
import com.cgi.ipsen5.Model.User;
import com.cgi.ipsen5.Service.FavoriteColleagueService;
import com.cgi.ipsen5.Service.ResetPasswordService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @GetMapping(path = "/{id}/get-favorite-collegues")
    public ApiResponse<List<UserResponseDTO>> getFavoriteColleaguesByUserId(@PathVariable UUID id){
        List<UserResponseDTO> favorites = new ArrayList<UserResponseDTO>();
        // todo be able to find the favs of a user
        return new ApiResponse<>(favorites);
    }

    @PostMapping(path = "/{id}/add-favorite-collegue")
    public void addFavoriteColleague(@PathVariable UUID id,
                                     @RequestBody UserAddFavoriteColleaguesDTO favoriteColleguesDTO){
        this.favoriteColleagueService.addFavoriteCollegue(id, favoriteColleguesDTO.getIdOfFavorite());
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
