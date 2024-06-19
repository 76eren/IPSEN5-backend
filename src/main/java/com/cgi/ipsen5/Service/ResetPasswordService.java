package com.cgi.ipsen5.Service;

import com.cgi.ipsen5.Dto.User.ResetPassword.ChangePasswordDTO;
import com.cgi.ipsen5.Dto.User.ResetPassword.ResetlinkRequestDTO;
import com.cgi.ipsen5.Exception.InvalidTokenException;
import com.cgi.ipsen5.Exception.UserNotFoundException;
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

        if (email == null || email.trim().isEmpty()) {
            throw new UsernameNotFoundException("User doesn't exist");
        }
        Optional<User> optionalUser = userService.findUserByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User doesn't exist");
        }
        else {
            User user = optionalUser.get();
            PasswordResetToken passwordResetToken = this.createPasswordResetTokenForUser(user);
            emailService.sendEmail(passwordResetToken.getId().toString(), user.getUsername());
        }

    }

    public void changePasswordOfUser(ChangePasswordDTO changePasswordDTO) throws UsernameNotFoundException {
        // check if token is valid
        String tokenIdAsString = changePasswordDTO.getToken();
        Optional<PasswordResetToken> optionalToken = tokenRepository.findById(UUID.fromString(tokenIdAsString));
        PasswordResetToken token = optionalToken.get();
        if(!this.isPasswordResetTokenValid(token.getId())){
            throw new InvalidTokenException("Reset token is invalid");
        }
        // extract userdata from dto
        String email = changePasswordDTO.getEmail();
        Optional<User> optionalUser = userService.findUserByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User doesn't exist");
        }
        else {
            String newPassword = changePasswordDTO.getPassword();
            User user = optionalUser.get();
            userService.resetPassword(user, newPassword);
        }
    }

    public boolean isPasswordResetTokenValidForUser(UUID tokenId, String userEmail) {
        User user = userService.findUserByEmail(userEmail)
                .orElseThrow(() ->  new UserNotFoundException("User not found"));

        Optional<PasswordResetToken> passTokenOptional = tokenRepository.findById(tokenId);
        if (passTokenOptional.isEmpty() || isTokenExpired(passTokenOptional.get())) {
            return false;
        }

        PasswordResetToken passToken = passTokenOptional.get();
        return passToken.getUser().getId().equals(user.getId());
    }

    private boolean isPasswordResetTokenValid(UUID tokenId) {
        Optional<PasswordResetToken> passTokenOptional = tokenRepository.findById(tokenId);
        if (passTokenOptional.isEmpty()) {
            return false;
        }
        PasswordResetToken passToken = passTokenOptional.get();
        if (isTokenExpired(passToken)) {
            this.tokenRepository.deleteById(tokenId);
            return false;
        }
        return true;
    }


    private boolean isTokenExpired(PasswordResetToken token) {
        final LocalDateTime now = LocalDateTime.now();
        return token.getExpiryDate().isBefore(now);
    }
}
