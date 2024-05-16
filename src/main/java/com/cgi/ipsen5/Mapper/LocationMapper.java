package com.cgi.ipsen5.Mapper;

import com.cgi.ipsen5.Dto.Reservation.Location.LocationDTO;
import com.cgi.ipsen5.Model.Location;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LocationMapper {
    public LocationDTO fromEntity(Location location) {

        return LocationDTO.builder()
                .name(location.getName())
                .type(location.getType())
                .createdAt(String.valueOf(location.getCreatedAt()))
                .capacity(location.getCapacity())
                .multireservable(location.isMultireservable())
                .wing(location.getWing())
                .build();
    }

}
