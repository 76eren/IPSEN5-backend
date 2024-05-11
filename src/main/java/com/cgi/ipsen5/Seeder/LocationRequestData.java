package com.cgi.ipsen5.Seeder;

import com.cgi.ipsen5.Dao.BuildingDao;
import com.cgi.ipsen5.Dao.FloorDao;
import com.cgi.ipsen5.Dao.UserDao;
import com.cgi.ipsen5.Dao.WingDao;
import com.cgi.ipsen5.Model.Building;
import com.cgi.ipsen5.Model.Floor;
import com.cgi.ipsen5.Model.Wing;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class LocationRequestData {
    // We use the DAOs to get the data and not the seeder because the repository edits the IDs afterward
    private final BuildingDao buildingDao;
    private final FloorDao floorDao;
    private final WingDao wingDao;
    private final UserDao userDao;


    // This will make the testing a lot less painful
    public void collectRandomLocationDataToMakeATestRequest() {
        // This method will just take some data from the seeders to make provide all the pieces for a test request
        String userId = this.userDao.findAllUsers().getFirst().getId().toString();
        Building building = this.buildingDao.getAll().getFirst();

        // Finds the floor where the building id is the same as building
        Floor floor = this.floorDao
                .findAll()
                .stream()
                .filter(f -> f.getBuilding()
                        .getId()
                        .equals(building.getId()))
                .findFirst()
                .get();


        // Finds the wing where the floor id is the same as floor
        Wing wing = this.wingDao
                .findAll()
                .stream()
                .filter(w -> w.getFloor()
                        .getId()
                        .equals(floor.getId()))
                .findFirst()
                .get();

        System.out.println(
                String.format("""
                                ========================================
                                To make a test request, you can use the following data:
                                User ID: %s
                                Building ID: %s
                                Floor ID: %s
                                Wing ID: %s
                                ========================================
                                """,
                        userId, building.getId(), floor.getId(), wing.getId())
        );




    }
}
