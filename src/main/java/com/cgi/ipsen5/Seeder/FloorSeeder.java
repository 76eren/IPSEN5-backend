package com.cgi.ipsen5.Seeder;

import com.cgi.ipsen5.Dao.BuildingDao;
import com.cgi.ipsen5.Dao.FloorDao;
import com.cgi.ipsen5.Model.Building;
import com.cgi.ipsen5.Model.Floor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.UUID;

@RequiredArgsConstructor
@Component
// All of this is temporary and for testing purposes
public class FloorSeeder {
    private final FloorDao floorDao;
    private final BuildingDao buildingDao;
    public void seed() {
        for (Floor i : getData()) {
            floorDao.save(i);
        }
    }

    public ArrayList<Floor> getData() {
        ArrayList<Building> buildings = (ArrayList<Building>) this.buildingDao.getAll();
        ArrayList<Floor> floors = new ArrayList<>();
        for (Building building : buildings) {
            for (int i = 0; i < 4; i++) {
                Floor floor = new Floor();
                floor.setBuilding(building);
                floor.setNumber(String.valueOf(i));
                floor.setId(UUID.randomUUID());
                floors.add(floor);
            }

        }

        return floors;

    }




}
