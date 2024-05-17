package com.cgi.ipsen5.Service;

import com.cgi.ipsen5.Model.PasswordResetToken;
import com.cgi.ipsen5.Model.User;
import com.cgi.ipsen5.Repository.PasswordTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ResetPasswordService {
    private final PasswordTokenRepository tokenRepository;

    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken resetToken = new PasswordResetToken(token, user);
        resetToken.setExpiryDate();
        tokenRepository.save(resetToken);
    }
}
