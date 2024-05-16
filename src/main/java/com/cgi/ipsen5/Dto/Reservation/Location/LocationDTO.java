package com.cgi.ipsen5.Dto.Reservation.Location;

import com.cgi.ipsen5.Dto.Reservation.Wing.WingDTO;
import com.cgi.ipsen5.Model.Wing;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LocationDTO {
    private Wing wing;
    private String name;
    private String type;
    private String createdAt;
    private int capacity;
    private boolean multireservable;
}
