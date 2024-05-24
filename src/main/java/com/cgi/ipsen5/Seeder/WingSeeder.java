package com.cgi.ipsen5.Seeder;

import com.cgi.ipsen5.Dao.FloorDao;
import com.cgi.ipsen5.Dao.WingDao;
import com.cgi.ipsen5.Model.Floor;
import com.cgi.ipsen5.Model.Wing;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@RequiredArgsConstructor
@Component
// All of this is temporary and for testing purposes
public class WingSeeder {
    private final FloorDao floorDao;
    private final WingDao wingDao;

    public void seed() {
        for (Wing wing : getData()) {
            this.wingDao.save(wing);
        }
    }

    public ArrayList<Wing> getData() {
        ArrayList<Wing> wings = new ArrayList<>();
        ArrayList<Floor> floors = this.floorDao.getAll();

        for (Floor floor : floors) {
            wings.add(createWing(floor, "A"));
            wings.add(createWing(floor, "B"));
            wings.add(createWing(floor, "C"));
            wings.add(createWing(floor, "D"));
        }


        return wings;
    }

    public Wing createWing(Floor floor, String name) {
        Floor managedFloor = floorDao.getById(floor.getId());

        return Wing
                .builder()
                .floor(managedFloor)
                .name(name)
                .build();
    }
}
