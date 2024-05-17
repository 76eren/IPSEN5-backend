package com.cgi.ipsen5.Service;

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

    public PasswordResetToken createPasswordResetTokenForUser(User user) {
        PasswordResetToken resetToken = new PasswordResetToken(user);
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

    private boolean isTokenFound(PasswordResetToken token) {
        return token != null;
    }

    private boolean isTokenExpired(PasswordResetToken token) {
        final LocalDateTime now = LocalDateTime.now();
        return token.getExpiryDate().isBefore(now);
    }
}
