package com.cgi.ipsen5.Dto.User;

import com.cgi.ipsen5.Model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: This will need to be updated
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEditDTO {
    private String username;
    private String password;
    private Role role;
}
