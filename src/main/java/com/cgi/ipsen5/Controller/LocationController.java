package com.cgi.ipsen5.Controller;

import com.cgi.ipsen5.Dao.LocationDao;
import com.cgi.ipsen5.Dto.Reservation.Location.LocationCreateDTO;
import com.cgi.ipsen5.Exception.BuildingNotFoundException;
import com.cgi.ipsen5.Exception.WingNotFoundException;
import com.cgi.ipsen5.Model.ApiResponse;
import com.cgi.ipsen5.Model.Location;
import com.cgi.ipsen5.Service.LocationService;
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
    private final LocationService locationService;

    @GetMapping
    public ApiResponse<List<Location>> getAllLocations() {
        return new ApiResponse<>(this.locationDao.getAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ApiResponse<Location> getById(@PathVariable UUID id) {
        return new ApiResponse<>(this.locationDao.getLocationById(id), HttpStatus.OK);
    }

    @GetMapping(value = "/admin")
    public ApiResponse<List<Location>> getLocationsByBuildingId(@RequestParam String buildingName) {
        return new ApiResponse<>(this.locationService.getLocationsByBuildingId(buildingName), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ApiResponse<Location> createNewLocation(@RequestBody LocationCreateDTO locationCreateDTO) {
        return new ApiResponse<>(this.locationService.createNewLocation(locationCreateDTO), HttpStatus.OK);
    }

    @ExceptionHandler({BuildingNotFoundException.class, WingNotFoundException.class})
    public ApiResponse<String> handleException(Exception e) {
        return new ApiResponse<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}

