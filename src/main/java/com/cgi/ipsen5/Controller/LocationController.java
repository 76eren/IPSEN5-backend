package com.cgi.ipsen5.Controller;

import com.cgi.ipsen5.Dao.LocationDao;
import com.cgi.ipsen5.Dto.Reservation.Location.LocationCreateEditDTO;
import com.cgi.ipsen5.Exception.BuildingNotFoundException;
import com.cgi.ipsen5.Exception.LocationNotFoundException;
import com.cgi.ipsen5.Exception.WingNotFoundException;
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

    @GetMapping(value = "/admin")
    public ApiResponse<List<Location>> getLocationsByBuildingId(@RequestParam String buildingName) {
        return new ApiResponse<>(this.locationService.getLocationsByBuildingId(buildingName), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ApiResponse<Location> createNewLocation(@RequestBody LocationCreateEditDTO locationCreateDTO) {
        return new ApiResponse<>(this.locationService.createNewLocation(locationCreateDTO), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/edit")
    public ApiResponse<Location> editLocation(
            @PathVariable UUID id,
            @RequestBody LocationCreateEditDTO locationEditDTO
    ) {
        return new ApiResponse<>(this.locationService.editLocation(id, locationEditDTO), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}/delete")
    public ApiResponse<String> deleteLocation(@PathVariable UUID id) {
        this.locationService.remove(id);
        return new ApiResponse<>("Deleted location successfully", HttpStatus.OK);
    }

    @ExceptionHandler({BuildingNotFoundException.class, WingNotFoundException.class, LocationNotFoundException.class})
    public ApiResponse<String> handleException(Exception e) {
        return new ApiResponse<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}

