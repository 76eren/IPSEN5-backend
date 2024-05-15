package com.cgi.ipsen5.Dto.Location.Floor;

import com.cgi.ipsen5.Dto.Location.Building.BuildingDTO;
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
