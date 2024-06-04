package com.cgi.ipsen5.Dto.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserStandardLocationDto {
    private UUID wingId;
    private UUID buildingId;
    private UUID floorId;
}
