package com.cgi.ipsen5.Dto.Auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthPasswordTokenRequestDTO {
    private UUID tokenId;
    String userEmail;
}
