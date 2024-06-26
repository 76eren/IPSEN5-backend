package com.cgi.ipsen5.Dto.User;

import com.cgi.ipsen5.Model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private UUID id;
    private String email; // This will represent the actual email of the user, aka the username field in the User model
    private String firstName;
    private String lastName;
    private Role role;
    private String phoneNumber;
}
