package com.cgi.ipsen5.Dto.Reserve.Floor;

import com.cgi.ipsen5.Dto.Reserve.Building.BuildingDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FloorDTO {
    private BuildingDTO building;
    private String number;

}
