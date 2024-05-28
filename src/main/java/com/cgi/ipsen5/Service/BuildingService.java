package com.cgi.ipsen5.Service;

import com.cgi.ipsen5.Dao.BuildingDao;
import com.cgi.ipsen5.Exception.BuildingNotFoundException;
import com.cgi.ipsen5.Model.Building;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BuildingService {
    private final BuildingDao buildingDao;

    public Building getBuildingByName(String buildingName) {
        Optional<Building> optionalBuilding = this.buildingDao.getBuildingByName(buildingName);

        if(optionalBuilding.isEmpty()) {
            throw new BuildingNotFoundException("Building not found");
        }

        return optionalBuilding.get();
    }
}