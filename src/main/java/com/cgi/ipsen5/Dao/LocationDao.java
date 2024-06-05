package com.cgi.ipsen5.Dao;


import com.cgi.ipsen5.Model.*;
import com.cgi.ipsen5.Repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
        return this.locationRepository.findAllByWing_FloorId(id);
    }

    public Location save(Location location) {
        return this.locationRepository.save(location);
    }

    public List<Location> findAllByWingId(UUID wingId) {
        return this.locationRepository.findAllByWingId(wingId);
    }

    public List<Location> findAllByBuildingId(UUID buildingId) {
        return this.locationRepository.findAllByWing_Floor_BuildingId(buildingId);
    }
}
