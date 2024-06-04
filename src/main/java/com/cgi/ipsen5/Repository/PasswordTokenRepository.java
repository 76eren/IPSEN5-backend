package com.cgi.ipsen5.Repository;

import com.cgi.ipsen5.Model.PasswordResetToken;
import com.cgi.ipsen5.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface PasswordTokenRepository extends JpaRepository<PasswordResetToken, UUID>{

    Optional<PasswordResetToken> findByUser(User user);

    List<PasswordResetToken> findByExpiryDateBefore(LocalDateTime now);
}
