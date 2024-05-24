package com.cgi.ipsen5.Dto.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDTO {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String role;
}
