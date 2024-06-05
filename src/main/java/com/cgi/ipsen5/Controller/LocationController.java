package com.cgi.ipsen5.Controller;

import com.cgi.ipsen5.Dao.LocationDao;
import com.cgi.ipsen5.Dto.Location.AvailableRoomsDTO;
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

    @GetMapping(value = "/available-rooms")
    public ApiResponse<List<Location>> getAvailableRooms(@RequestBody AvailableRoomsDTO availableRoomsDTO) {
        return new ApiResponse<>(this.locationService
                .findAvailableRooms( availableRoomsDTO.getBuildingId(),
                        availableRoomsDTO.getNumberOfPeople(),
                        LocalDateTime.parse(availableRoomsDTO.getStartDateTime()),
                        LocalDateTime.parse(availableRoomsDTO.getEndDateTime())),
                HttpStatus.OK);
    }

}

