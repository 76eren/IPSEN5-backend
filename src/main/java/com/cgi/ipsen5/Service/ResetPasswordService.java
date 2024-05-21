package com.cgi.ipsen5.Service;

import com.cgi.ipsen5.Dto.Auth.PasswordRequestDTO;
import com.cgi.ipsen5.Exception.UsernameNotFoundException;
import com.cgi.ipsen5.Model.PasswordResetToken;
import com.cgi.ipsen5.Model.User;
import com.cgi.ipsen5.Repository.PasswordTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

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

    public String validatePasswordResetToken(PasswordResetToken token) {
        Optional<PasswordResetToken> passTokenOptional = tokenRepository.findById(token.getId());

        if (passTokenOptional.isEmpty()) {
            return "invalidToken";
        }
        PasswordResetToken passToken = passTokenOptional.get();
        if (isTokenExpired(passToken)) {
            return "expired";
        }
        return null;
    }

    public void requestResetLink(PasswordRequestDTO passwordRequestDTO) throws UsernameNotFoundException {
        String email = passwordRequestDTO.getEmail();
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

    private boolean isTokenFound(PasswordResetToken token) {
        return token != null;
    }

    private boolean isTokenExpired(PasswordResetToken token) {
        final LocalDateTime now = LocalDateTime.now();
        return token.getExpiryDate().isBefore(now);
    }
}
