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
import jakarta.validation.Valid;
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
        return new ApiResponse<>(this.locationService.getLocationsByBuildingName(buildingName), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ApiResponse<Location> createNewLocation(@Valid @RequestBody LocationCreateEditDTO locationCreateDTO) {
        Location location = this.locationService.createNewLocation(locationCreateDTO);

        if(location == null) {
            return new ApiResponse<>("Could not save the location", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ApiResponse<>(location, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/edit")
    public ApiResponse<Location> editLocation(
            @PathVariable UUID id,
            @Valid
            @RequestBody LocationCreateEditDTO locationEditDTO
    ) {
        Location location = this.locationService.editLocation(id, locationEditDTO);

        if(location == null) {
            return new ApiResponse<>("Could not save the location", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ApiResponse<>(location, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}/delete")
    public ApiResponse<String> deleteLocation(@PathVariable UUID id) {
        this.locationService.remove(id);
        return new ApiResponse<>("Deleted location successfully", HttpStatus.OK);
    }

    @ExceptionHandler({BuildingNotFoundException.class, WingNotFoundException.class, LocationNotFoundException.class})
    public ApiResponse<String> handleException(Exception e) {
        return new ApiResponse<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}

