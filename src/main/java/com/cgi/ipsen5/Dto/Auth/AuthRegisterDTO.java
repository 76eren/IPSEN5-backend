package com.cgi.ipsen5.Dto.Auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthRegisterDTO {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String phoneNumber;
}