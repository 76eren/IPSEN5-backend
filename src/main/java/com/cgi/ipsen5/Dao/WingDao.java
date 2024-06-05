package com.cgi.ipsen5.Dao;

import com.cgi.ipsen5.Exception.WingNotFoundException;
import com.cgi.ipsen5.Model.Floor;
import com.cgi.ipsen5.Model.Wing;
import com.cgi.ipsen5.Repository.WingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class WingDao {
    private final WingRepository wingRepository;

    public void save(Wing wing) {
        this.wingRepository.save(wing);
    }

    public ArrayList<Wing> findAll() {
        return (ArrayList<Wing>) this.wingRepository.findAll();
    }

    public Wing findWingById(UUID wingId) {
        return this.wingRepository.findById(wingId).orElse(null);
    }

    public boolean existsById(UUID wingId) {
        return this.wingRepository.existsById(wingId);
    }
  
    public ArrayList<Wing> findWingsByBuildingId(UUID buildingId) {
        return this.wingRepository.findWingsByFloor_BuildingId(buildingId);
    }

    public List<Wing> findAllByFloorNumber(UUID floorId) {
        return this.wingRepository.findAllByFloorId(floorId)
                .orElseThrow(() -> new WingNotFoundException("Wing not found"));
    }
}
