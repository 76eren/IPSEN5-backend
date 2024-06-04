package com.cgi.ipsen5.Dto.Reservation.Location;

import com.cgi.ipsen5.Model.Wing;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationCreateEditDTO {
    private Wing wing;
    private String name;
    private String type;
    private int capacity;
}
