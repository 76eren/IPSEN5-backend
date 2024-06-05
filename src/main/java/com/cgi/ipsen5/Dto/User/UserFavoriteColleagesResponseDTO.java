package com.cgi.ipsen5.Dto.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserFavoriteColleagesResponseDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String standardLocation;
}
