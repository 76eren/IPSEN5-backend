package com.cgi.ipsen5.Dao;


import com.cgi.ipsen5.Model.*;
import com.cgi.ipsen5.Repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class LocationDao {
    private final LocationRepository locationRepository;

    public List<Location> getAll() {
        return this.locationRepository.findAll();
    }

    public Location getLocationById(UUID id) {
        return this.locationRepository.findById(id).orElse(null);
    }

    public List<Floor> getFloorByLocationId(UUID id) {
        return this.locationRepository.findFloorsByLocation_Id(id);
    }
}
