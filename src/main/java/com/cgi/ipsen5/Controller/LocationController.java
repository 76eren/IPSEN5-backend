package com.cgi.ipsen5.Controller;

import com.cgi.ipsen5.Dao.LocationDao;
import com.cgi.ipsen5.Model.ApiResponse;
import com.cgi.ipsen5.Model.Location;
import com.cgi.ipsen5.Service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
        return new ApiResponse<>(this.locationService.getLocationById(id), HttpStatus.OK);
    }

    //TODO: find solution for req params
    @GetMapping(value = "/available-rooms")
    public ApiResponse<List<Location>> getAvailableRooms(
            @RequestParam UUID buildingId,
            @RequestParam Integer numberOfPeople,
            @RequestParam String startDateTime,
            @RequestParam String endDateTime) {
        return new ApiResponse<>(this.locationService
                .findAvailableRooms( buildingId,
                        numberOfPeople,
                        LocalDateTime.parse(startDateTime),
                        LocalDateTime.parse(endDateTime)),
                HttpStatus.OK);
    }

}

