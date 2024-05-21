package com.cgi.ipsen5.Dao;

import com.cgi.ipsen5.Model.Floor;
import com.cgi.ipsen5.Repository.FloorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FloorDao {
    private final FloorRepository floorRepository;

    public void save(Floor floor) {
        floorRepository.save(floor);
    }

    public ArrayList<Floor> findAll() {
        return (ArrayList<Floor>) floorRepository.findAll();
    }

    public Floor findById(UUID id) {
        return floorRepository.findById(id).orElseThrow(() -> new RuntimeException("Floor not found"));
    }
}
