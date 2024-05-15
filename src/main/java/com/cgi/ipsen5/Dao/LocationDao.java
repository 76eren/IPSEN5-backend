package com.cgi.ipsen5.Dao;


import com.cgi.ipsen5.Dto.Location.LocationCreateDTO;
import com.cgi.ipsen5.Model.*;
import com.cgi.ipsen5.Repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class LocationDao {
    private final LocationRepository locationRepository;

    public Location create(LocationCreateDTO locationCreateDTO) {
        Location location = this.findLocationById(UUID.fromString(locationCreateDTO.getLocationId()));
        return this.save(location);
    }

    private Location findLocationById(UUID locationId) {
        return this.locationRepository.findById(locationId).orElse(null);
    }

    private LocalDateTime stringToLocalDate(String date) {
        // convert a string (for example '2024-05-11T14:30:00') to a LocalDateTime object
        return LocalDateTime.parse(date);
    }

    private String localDateToString(LocalDateTime date) {
        return date.toString();
    }


    public Location save(Location location) {
        return this.locationRepository.save(location);
    }
}
