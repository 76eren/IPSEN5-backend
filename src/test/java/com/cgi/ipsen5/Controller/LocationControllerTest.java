package com.cgi.ipsen5.Controller;

import com.cgi.ipsen5.Controller.LocationController;
import com.cgi.ipsen5.Dao.LocationDao;
import com.cgi.ipsen5.Dto.Reservation.Location.LocationCreateEditDTO;
import com.cgi.ipsen5.Model.ApiResponse;
import com.cgi.ipsen5.Model.Location;
import com.cgi.ipsen5.Service.LocationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class LocationControllerTest {
    private final LocationService locationService = Mockito.mock(LocationService.class);
    private final LocationDao locationDao = Mockito.mock(LocationDao.class);
    private final LocationController locationController = new LocationController(locationDao, locationService);

    @Test
    public void test_create_new_location() {
        LocationCreateEditDTO locationCreateDTO = new LocationCreateEditDTO();
        Location expectedLocation = new Location();
        when(locationService.createNewLocation(locationCreateDTO)).thenReturn(expectedLocation);
        ApiResponse<Location> response = locationController.createNewLocation(locationCreateDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        boolean containsExpectedLocation = response.toString().contains(expectedLocation.toString());
        assertTrue(containsExpectedLocation);
    }

    @Test
    public void test_edit_location() {
        UUID id = UUID.fromString("60713ce0-0e24-4015-af40-d0c42c3899ef");
        LocationCreateEditDTO locationEditDTO = new LocationCreateEditDTO();
        Location expectedLocation = new Location();
        when(locationService.editLocation(id, locationEditDTO)).thenReturn(expectedLocation);
        ApiResponse<Location> response = locationController.editLocation(id, locationEditDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        boolean containsExpectedLocation = response.toString().contains(expectedLocation.toString());
        assertTrue(containsExpectedLocation);

    }
}