package com.cgi.ipsen5.Service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserVerification {
    public boolean verifyUser(Authentication authentication, String id) {
        // TODO: Add logic for the admin to be able to bypass this
        return authentication.getName().equals(id);
    }
}
