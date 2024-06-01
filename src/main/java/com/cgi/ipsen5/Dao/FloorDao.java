package com.cgi.ipsen5.Dao;

import com.cgi.ipsen5.Exception.FloorNotFoundException;
import com.cgi.ipsen5.Model.Floor;
import com.cgi.ipsen5.Repository.FloorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FloorDao {
    private final FloorRepository floorRepository;

    public void save(Floor floor) {
        floorRepository.save(floor);
    }

    public ArrayList<Floor> getAll() {
        return (ArrayList<Floor>) floorRepository.findAll();
    }

    public Floor getById(UUID id) {
        return floorRepository.findById(id).orElseThrow(() -> new RuntimeException("Floor not found"));
    }

    public List<Floor> getAllByBuildingId(UUID buildingId) {
        return floorRepository.findAllByBuildingId(buildingId)
                .orElseThrow(() -> new FloorNotFoundException("Floor not found"));
    }
}
