package com.cgi.ipsen5.Dao;

import com.cgi.ipsen5.Model.Building;
import com.cgi.ipsen5.Repository.BuildingRepository;
import com.cgi.ipsen5.Seeder.BuildingSeeder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BuildingDao {
    private final BuildingRepository buildingRepository;

    public void save(Building building) {
        this.buildingRepository.save(building);
    }
}
