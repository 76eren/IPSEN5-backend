package com.cgi.ipsen5.Seeder;

import com.cgi.ipsen5.Dao.LocationDao;
import com.cgi.ipsen5.Dao.WingDao;
import com.cgi.ipsen5.Model.Location;
import com.cgi.ipsen5.Model.Wing;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;

@RequiredArgsConstructor
@Component
public class LocationSeeder {
    private final LocationDao locationDao;
    private final WingDao wingDao;

    public void seed() {
        for (Location i : getLocations()) {
            locationDao.save(i);
        }

    }


    private ArrayList<Location> getLocations() {
        ArrayList<Location> locations = new ArrayList<>();
        ArrayList<Wing> wings = wingDao.findAll();

        for (Wing i : wings) {
            locations.add(createLocation("A1", i, LocalDateTime.now()));
            locations.add(createLocation("A2", i, LocalDateTime.now()));
            locations.add(createLocation("A3", i, LocalDateTime.now()));
            locations.add(createLocation("A4", i, LocalDateTime.now()));
            locations.add(createLocation("A5", i, LocalDateTime.now()));
            locations.add(createLocation("A6", i, LocalDateTime.now()));
            locations.add(createLocation("A7", i, LocalDateTime.now()));
            locations.add(createLocation("A8", i, LocalDateTime.now()));
            locations.add(createLocation("B1", i, LocalDateTime.now()));
            locations.add(createLocation("B2", i, LocalDateTime.now()));
            locations.add(createLocation("B3", i, LocalDateTime.now()));
            locations.add(createLocation("B4", i, LocalDateTime.now()));
        }


        return locations;
    }

    private Location createLocation(String name, Wing wing, LocalDateTime createdAt) {
        Wing managedWing = wingDao.findWingById(wing.getId());


        return Location
                .builder()
                .name(name)
                .wing(managedWing)
                .createdAt(createdAt)
                .capacity(30)
                .type("WORKPLACE")
                .multireservable(true)
                .build();
    }

}
