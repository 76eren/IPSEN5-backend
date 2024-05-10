package com.cgi.ipsen5.Seeder;


import com.cgi.ipsen5.Dao.BuildingDao;
import com.cgi.ipsen5.Model.Building;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class BuildingSeeder {
    // All of this is temporary and for testing purposes

    private final BuildingDao buildingDao;

    public void seed() {
        for (Building i : getBuildings()) {
            buildingDao.save(i);
        }

    }


    public ArrayList<Building> getBuildings() {
        ArrayList<Building> buildings = new ArrayList<>();
        buildings.add(new Building(UUID.randomUUID(), "De Entree 21 1101 BH "));
        buildings.add(new Building(UUID.randomUUID(), "Utrechtseweg 310 / gebouw B42 6812 AR"));
        buildings.add(new Building(UUID.randomUUID(), "DHigh Tech Campus 5 5656 AE"));
        buildings.add(new Building(UUID.randomUUID(), "Eemsgolaan 1 9727 DW"));
        buildings.add(new Building(UUID.randomUUID(), "Stationsplein 12 6221 BT"));
        buildings.add(new Building(UUID.randomUUID(), "George Hintzenweg 89 3068 AX"));
        return buildings;
    }
}
