package com.cgi.ipsen5.Seeder;

import com.cgi.ipsen5.Dao.LocationDao;
import com.cgi.ipsen5.Dao.WingDao;
import com.cgi.ipsen5.Model.Location;
import com.cgi.ipsen5.Model.LocationType;
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

        for (Wing wing : wings) {
            locations.add(createLocation(wing.getFloor().getNumber()+ "."+ wing.getName()+"1", wing, LocalDateTime.now()));
            locations.add(createLocation(wing.getFloor().getNumber()+ "."+ wing.getName()+"2", wing, LocalDateTime.now()));
            locations.add(createLocation(wing.getFloor().getNumber()+ "."+ wing.getName()+"3", wing, LocalDateTime.now()));
            locations.add(createLocation(wing.getFloor().getNumber()+ "."+ wing.getName()+"4", wing, LocalDateTime.now()));
            locations.add(createLocation(wing.getFloor().getNumber()+ "."+ wing.getName()+"5", wing, LocalDateTime.now()));
            locations.add(createLocation(wing.getFloor().getNumber()+ "."+ wing.getName()+"6", wing, LocalDateTime.now()));
            locations.add(createLocation(wing.getFloor().getNumber()+ "."+ wing.getName()+"7", wing, LocalDateTime.now()));
            locations.add(createLocation(wing.getFloor().getNumber()+ "."+ wing.getName()+"8", wing, LocalDateTime.now()));
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
                .type(LocationType.WORKPLACE)
                .multireservable(true)
                .build();
    }

}
