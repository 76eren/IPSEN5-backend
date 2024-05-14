package com.cgi.ipsen5.Repository;

import com.cgi.ipsen5.Model.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface PasswordTokenRepository extends JpaRepository<PasswordResetToken, UUID>{
}
