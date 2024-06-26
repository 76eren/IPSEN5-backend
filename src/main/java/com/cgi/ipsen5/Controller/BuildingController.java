package com.cgi.ipsen5.Controller;


import com.cgi.ipsen5.Dao.WingDao;
import com.cgi.ipsen5.Model.ApiResponse;
import com.cgi.ipsen5.Model.Building;
import com.cgi.ipsen5.Model.Wing;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cgi.ipsen5.Dao.BuildingDao;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/building")
@RequiredArgsConstructor
public class BuildingController {
    private final BuildingDao buildingDao;
    private final WingDao wingDao;


    @GetMapping
    public ApiResponse<List<Building>> getAllBuildings() {
        return new ApiResponse<>(this.buildingDao.getAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ApiResponse<Building> getBuildingById(@PathVariable UUID id) {
        return new ApiResponse<>(this.buildingDao.getBuildingById(id), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/wing")
    public ApiResponse<List<Wing>> getWingByBuildingId(@PathVariable UUID id) {
        return new ApiResponse<>(this.wingDao.findWingsByBuildingId(id), HttpStatus.OK);
    }
}
