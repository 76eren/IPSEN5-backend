package com.cgi.ipsen5.Repository;

import com.cgi.ipsen5.Model.Wing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.UUID;

public interface WingRepository extends JpaRepository<Wing, UUID> {

//    public ArrayList<Wing> findWingByFloorBuilding_Id(UUID buildingId);
    public ArrayList<Wing> findWingsByFloor_BuildingId(UUID buildingId);
}
