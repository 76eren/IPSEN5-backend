package com.cgi.ipsen5.Repository;

import com.cgi.ipsen5.Model.Wing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WingRepository extends JpaRepository<Wing, UUID> {
}
