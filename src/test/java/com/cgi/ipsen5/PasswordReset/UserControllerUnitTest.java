package com.cgi.ipsen5.PasswordReset;

import com.cgi.ipsen5.Controller.UserController;
import com.cgi.ipsen5.Dao.UserDao;
import com.cgi.ipsen5.Dto.User.ResetPassword.ChangePasswordDTO;
import com.cgi.ipsen5.Dto.User.ResetPassword.ResetlinkRequestDTO;
import com.cgi.ipsen5.Exception.UsernameNotFoundException;
import com.cgi.ipsen5.Mapper.UserMapper;
import com.cgi.ipsen5.Service.ResetPasswordService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerUnitTest {
    @InjectMocks
    private UserController userController;

    @Mock
    private ResetPasswordService resetPasswordService;

    @Test
    public void testRequestResetPassword_Success() throws UsernameNotFoundException {
        ResetlinkRequestDTO request = new ResetlinkRequestDTO();
        request.setEmail("test@example.com");

        doNothing().when(resetPasswordService).requestResetLink(any(ResetlinkRequestDTO.class));

        userController.requestResetPassword(request);

        verify(resetPasswordService, times(1)).requestResetLink(request);
    }

    @Test
    public void testChangePassword_Success() throws UsernameNotFoundException {
        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO();
        changePasswordDTO.setEmail("test@example.com");
        changePasswordDTO.setPassword("newPassword");
        changePasswordDTO.setToken("valid-token");

        doNothing().when(resetPasswordService).changePasswordOfUser(any(ChangePasswordDTO.class));

        userController.changePassword(changePasswordDTO);

        verify(resetPasswordService, times(1)).changePasswordOfUser(changePasswordDTO);
    }

    @Test
    public void testUnhappyFlow_RequestResetPassword_UserNotFound() throws UsernameNotFoundException {
        ResetlinkRequestDTO request = new ResetlinkRequestDTO();
        request.setEmail("unknown@example.com");

        doThrow(new UsernameNotFoundException("User doesn't exist")).when(resetPasswordService).requestResetLink(any(ResetlinkRequestDTO.class));

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> {
            userController.requestResetPassword(request);
        });

        assertEquals("User doesn't exist", exception.getMessage());
    }

    @Test
    public void testUnhappyFlow_ChangePassword_InvalidToken() throws UsernameNotFoundException {
        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO();
        changePasswordDTO.setEmail("test@example.com");
        changePasswordDTO.setPassword("newPassword");
        changePasswordDTO.setToken("invalid-token");

        doThrow(new UsernameNotFoundException("Invalid token")).when(resetPasswordService).changePasswordOfUser(any(ChangePasswordDTO.class));

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> {
            userController.changePassword(changePasswordDTO);
        });

        assertEquals("Invalid token", exception.getMessage());
    }


    @Test
    public void testChangePassword_EmptyPassword() throws UsernameNotFoundException {
        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO();
        changePasswordDTO.setEmail("test@example.com");
        changePasswordDTO.setPassword("");
        changePasswordDTO.setToken("valid-token");

        doThrow(new IllegalArgumentException("Password cannot be empty")).when(resetPasswordService).changePasswordOfUser(any(ChangePasswordDTO.class));

        try {
            userController.changePassword(changePasswordDTO);
            fail("Expected IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("Password cannot be empty", e.getMessage());
        }
    }
}
