package com.cgi.ipsen5.Service;

import com.cgi.ipsen5.Dto.User.ResetPassword.ChangePasswordDTO;
import com.cgi.ipsen5.Dto.User.ResetPassword.ResetlinkRequestDTO;
import com.cgi.ipsen5.Exception.UsernameNotFoundException;
import com.cgi.ipsen5.Model.PasswordResetToken;
import com.cgi.ipsen5.Model.User;
import com.cgi.ipsen5.Repository.PasswordTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ResetPasswordService {
    private final PasswordTokenRepository tokenRepository;
    private final UserService userService;
    private final ResetlinkEmailService emailService;

    public PasswordResetToken createPasswordResetTokenForUser(User user) {
        Optional<PasswordResetToken> existingToken = tokenRepository.findByUser(user);

        PasswordResetToken resetToken = existingToken.orElseGet(() -> new PasswordResetToken(user));
        resetToken.setExpiryDate();
        tokenRepository.save(resetToken);
        return resetToken;
    }

    public void requestResetLink(ResetlinkRequestDTO resetlinkRequestDTO) throws UsernameNotFoundException {
        String email = resetlinkRequestDTO.getEmail();
        Optional<User> optionalUser = userService.findUserByEmail(email);
        if (!optionalUser.isPresent()) {
            throw new UsernameNotFoundException("User doesn't exist");
        }
        else {
            User user = optionalUser.get();
            PasswordResetToken passwordResetToken = this.createPasswordResetTokenForUser(user);
            emailService.sendEmail(passwordResetToken.getId().toString(), user.getUsername());
        }

    }

    public void changePasswordOfUser(ChangePasswordDTO changePasswordDTO, UUID tokenId) throws UsernameNotFoundException {
        // check if token is valid
        Optional<PasswordResetToken> optionalToken = tokenRepository.findById(tokenId);
        PasswordResetToken token = optionalToken.get();
        if(!this.isPasswordResetTokenValid(token)){
            return;
        }
        // extract userdata from dto
        String email = changePasswordDTO.getEmail();
        Optional<User> optionalUser = userService.findUserByEmail(email);
        if (!optionalUser.isPresent()) {
            throw new UsernameNotFoundException("User doesn't exist");
        }
        else {
            String newPassword = changePasswordDTO.getPassword();
            User user = optionalUser.get();
            userService.resetPassword(user, newPassword);
        }
    }

    private boolean isPasswordResetTokenValid(PasswordResetToken token) {
        Optional<PasswordResetToken> passTokenOptional = tokenRepository.findById(token.getId());

        if (passTokenOptional.isEmpty()) {
            return false;
        }
        PasswordResetToken passToken = passTokenOptional.get();
        if (isTokenExpired(passToken)) {
            return false;
        }
        return true;
    }


    private boolean isTokenExpired(PasswordResetToken token) {
        final LocalDateTime now = LocalDateTime.now();
        return token.getExpiryDate().isBefore(now);
    }
}
