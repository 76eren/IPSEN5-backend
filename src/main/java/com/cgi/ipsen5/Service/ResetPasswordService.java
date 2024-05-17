package com.cgi.ipsen5.Service;

import com.cgi.ipsen5.Model.PasswordResetToken;
import com.cgi.ipsen5.Model.User;
import com.cgi.ipsen5.Repository.PasswordTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class ResetPasswordService {
    private final PasswordTokenRepository tokenRepository;

    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken resetToken = new PasswordResetToken(token, user);
        resetToken.setExpiryDate();
        tokenRepository.save(resetToken);
    }

    public String validatePasswordResetToken(String token) {
        final PasswordResetToken passToken = tokenRepository.findByToken(token);

        return !isTokenFound(passToken) ? "invalidToken"
                : isTokenExpired(passToken) ? "expired"
                : null;
    }

    private boolean isTokenFound(PasswordResetToken passToken) {
        return passToken != null;
    }

    private boolean isTokenExpired(PasswordResetToken passToken) {
        final LocalDateTime now = LocalDateTime.now();
        return passToken.getExpiryDate().isBefore(now);
    }
}
