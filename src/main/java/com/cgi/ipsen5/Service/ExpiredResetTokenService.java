package com.cgi.ipsen5.Service;

import com.cgi.ipsen5.Model.PasswordResetToken;
import com.cgi.ipsen5.Repository.PasswordTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ExpiredResetTokenService {
    private final PasswordTokenRepository tokenRepository;

    @Transactional
    public void removeExpiredTokens(){
        LocalDateTime now = LocalDateTime.now();
        List<PasswordResetToken> expiredTokens = tokenRepository.findByExpiryDateBefore(now);
        tokenRepository.deleteAll(expiredTokens);
    }
}
