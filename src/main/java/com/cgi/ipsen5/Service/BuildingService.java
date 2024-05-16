package com.cgi.ipsen5.Service;

import com.cgi.ipsen5.Dao.BuildingDao;
import com.cgi.ipsen5.Dto.Reserve.Building.BuildingDTO;
import com.cgi.ipsen5.Model.Building;
import com.cgi.ipsen5.Mapper.BuildingMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BuildingService {
    private final BuildingDao buildingDao;
    private final BuildingMapper buildingMapper;

    public ArrayList<BuildingDTO> getBuildings() {
        List<Building> buildings = this.buildingDao.getAll();
        ArrayList<BuildingDTO> buildingDTOs = new ArrayList<>();

        if (buildings == null) {
            return new ArrayList<>();
        }

        for (Building building : buildings) {
            buildingDTOs.add(this.buildingMapper.fromEntity(building));
        }

        return buildingDTOs;
    }

    public BuildingDTO getBuildingById(UUID id) {
        Building buildings = this.buildingDao.getBuildingById(id);
        BuildingDTO buildingDTO = new BuildingDTO();

        if (buildings == null) {
            return null;
        }

        buildingDTO = this.buildingMapper.fromEntity(buildings);

        return buildingDTO;
    }
}