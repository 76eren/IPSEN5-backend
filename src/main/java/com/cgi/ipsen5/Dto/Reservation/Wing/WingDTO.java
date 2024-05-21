package com.cgi.ipsen5.Dto.Reservation.Wing;

import com.cgi.ipsen5.Dto.Reservation.Floor.FloorDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WingDTO {
    private FloorDTO floor;
    private String name;
}
