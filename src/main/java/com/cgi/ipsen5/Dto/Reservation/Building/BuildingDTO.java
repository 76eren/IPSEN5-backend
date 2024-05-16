package com.cgi.ipsen5.Dto.Reservation.Building;

import com.cgi.ipsen5.Model.Building;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BuildingDTO {
    private String name;
    private String address;
}
