package com.cgi.ipsen5.Repository;

import com.cgi.ipsen5.Model.InvalidToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvalidTokenRepository extends JpaRepository<InvalidToken, String> {
}