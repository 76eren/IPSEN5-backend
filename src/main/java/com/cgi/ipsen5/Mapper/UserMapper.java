package com.cgi.ipsen5.Mapper;

import com.cgi.ipsen5.Dto.User.UserResponseDTO;
import com.cgi.ipsen5.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
    public UserResponseDTO fromEntity(User user) {

        return UserResponseDTO
                .builder()
                .id(user.getId())
                .email(user.getUsername())
                .firstName(user.getFirstName())
                .role(user.getRole())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }
}
