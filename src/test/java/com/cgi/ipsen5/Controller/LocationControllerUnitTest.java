package com.cgi.ipsen5.Controller;

import com.cgi.ipsen5.Dao.LocationDao;
import com.cgi.ipsen5.Dto.Reservation.Location.LocationCreateEditDTO;
import com.cgi.ipsen5.Model.ApiResponse;
import com.cgi.ipsen5.Model.Location;
import com.cgi.ipsen5.Model.LocationType;
import com.cgi.ipsen5.Model.Wing;
import com.cgi.ipsen5.Service.LocationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LocationControllerUnitTest {
    @Mock
    private LocationService locationService;

    @Mock
    private LocationDao locationDao;
    private LocationController SUT;
    private Location dummyLocation;

    @BeforeEach
    public void setUp() {
        this.SUT = new LocationController(locationDao, locationService);
        this.dummyLocation = new Location(
                UUID.randomUUID(),
                Wing.builder().build(),
                "A3.01",
                LocationType.ROOM,
                6,
                false,
                LocalDateTime.now(),
                false
        );
    }

    @Test
    public void should_return_http_status_ok_when_createNewLocation_succeeds() {
        LocationCreateEditDTO locationCreateDTO = new LocationCreateEditDTO();

        when(this.locationService.createNewLocation(any(LocationCreateEditDTO.class))).thenReturn(this.dummyLocation);

        ApiResponse<Location> response = this.SUT.createNewLocation(locationCreateDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void should_return_http_status_internal_server_error_when_createNewLocation_fails() {
        LocationCreateEditDTO locationCreateDTO = new LocationCreateEditDTO();

        when(this.locationService.createNewLocation(any(LocationCreateEditDTO.class))).thenReturn(null);

        ApiResponse<Location> response = this.SUT.createNewLocation(locationCreateDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void should_return_http_status_ok_when_editLocation_succeeds() {
        LocationCreateEditDTO locationEditDTO = new LocationCreateEditDTO();

        when(this.locationService.editLocation(any(UUID.class), any(LocationCreateEditDTO.class))).thenReturn(this.dummyLocation);

        ApiResponse<Location> response = this.SUT.editLocation(UUID.randomUUID(), locationEditDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void should_return_http_status_internal_server_error_when_editLocation_fails() {
        LocationCreateEditDTO locationEditDTO = new LocationCreateEditDTO();

        when(this.locationService.editLocation(any(UUID.class), any(LocationCreateEditDTO.class))).thenReturn(null);

        ApiResponse<Location> response = this.SUT.editLocation(UUID.randomUUID(), locationEditDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}
