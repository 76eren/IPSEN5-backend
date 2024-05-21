package com.cgi.ipsen5.Controller;

import com.cgi.ipsen5.Dao.LocationDao;
import com.cgi.ipsen5.Model.ApiResponse;
import com.cgi.ipsen5.Model.Floor;
import com.cgi.ipsen5.Model.Location;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/location")
@RequiredArgsConstructor
public class LocationController {
    private final LocationDao locationDao;

    @GetMapping
    public ApiResponse<List<Location>> getAllLocations() {
        return new ApiResponse<>(this.locationDao.getAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ApiResponse<Location> getById(@PathVariable UUID id) {
        return new ApiResponse<>(this.locationDao.getLocationById(id), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/floor")
    public ApiResponse<List<Floor>> getFloorByLocationId(@PathVariable UUID id) {
        return new ApiResponse<>(this.locationDao.getFloorByLocationId(id), HttpStatus.OK);
    }

}

