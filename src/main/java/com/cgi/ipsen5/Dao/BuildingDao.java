package com.cgi.ipsen5.Dao;

import com.cgi.ipsen5.Model.Building;
import com.cgi.ipsen5.Repository.BuildingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BuildingDao {
    private final BuildingRepository buildingRepository;

    public void save(Building building) {
        this.buildingRepository.save(building);
    }

    public List<Building> getAll() {
        return this.buildingRepository.findAll();
    }

    public Building getBuildingById(UUID buildingId) {
        if (buildingId == null) {
            throw new IllegalArgumentException("Building ID cannot be null");
        }
        return this.buildingRepository.findById(buildingId).orElseThrow(() -> new RuntimeException("Building not found"));
    }
}
