package com.cgi.ipsen5.Service;

import com.cgi.ipsen5.Dao.LocationDao;
import com.cgi.ipsen5.Dao.ReservationDao;
import com.cgi.ipsen5.Dto.Reservation.Location.LocationCreateEditDTO;
import com.cgi.ipsen5.Exception.LocationNotFoundException;
import com.cgi.ipsen5.Exception.WingNotFoundException;
import com.cgi.ipsen5.Model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LocationServiceUnitTest {
    @Mock
    private LocationDao locationDao;

    @Mock
    private ReservationDao reservationDao;

    @Mock
    private BuildingService buildingService;

    @Mock
    private WingService wingService;

    private LocationService SUT;
    private Building dummyBuilding;
    private Floor dummyFloor;
    private Wing dummyWing;
    private Location dummyLocation;

    @BeforeEach
    public void setUp() {
        this.SUT = new LocationService(locationDao, reservationDao, buildingService, wingService);
        this.dummyBuilding = new Building(UUID.randomUUID(), "Amsterdam", "De Entree 21 1101 BH");
        this.dummyFloor = new Floor(UUID.randomUUID(), dummyBuilding, "3");
        this.dummyWing = new Wing(UUID.randomUUID(), dummyFloor, "A3");
        this.dummyLocation = new Location(UUID.randomUUID(), dummyWing, "A3.01", LocationType.ROOM, 6, false, LocalDateTime.now(), false);
    }

    @Test
    public void should_get_all_locations_by_building_id(){
        List<Location> locations = new ArrayList<>();
        locations.add(dummyLocation);

        when(buildingService.getBuildingByName(any(String.class))).thenReturn(this.dummyBuilding);
        when(locationDao.findAllByBuildingId(any(UUID.class))).thenReturn(locations);

        List<Location> result = this.SUT.getLocationsByBuildingName("Amsterdam");

        assertNotNull(result);
        assertThat(result, is(locations));
    }

    @Test
    public void should_save_location_when_createNewLocation_is_called() {
        LocationCreateEditDTO locationCreateDTO = new LocationCreateEditDTO(
                dummyWing, "Amsterdam", LocationType.ROOM, 6
        );

        when(this.wingService.getWingById(any(UUID.class))).thenReturn(this.dummyWing);
        when(this.locationDao.save(any(Location.class))).thenReturn(this.dummyLocation);

        Location result = this.SUT.createNewLocation(locationCreateDTO);

        assertNotNull(result);
        assertThat(result, is(this.dummyLocation));
    }

    @Test
    public void should_throw_not_found_exception_when_wing_does_not_exist_create() {
        LocationCreateEditDTO locationCreateDTO = new LocationCreateEditDTO(
                dummyWing, "Amsterdam", LocationType.ROOM, 6
        );

        when(this.wingService.getWingById(any(UUID.class))).thenThrow(new WingNotFoundException("Wing not found"));

        assertThrows(WingNotFoundException.class, () -> {
            this.SUT.createNewLocation(locationCreateDTO);
        });
    }

    @Test
    public void should_edit_location_when_editLocation_is_called() {
        LocationCreateEditDTO locationEditDTO = new LocationCreateEditDTO(
                dummyWing, "Amsterdam", LocationType.ROOM, 6
        );

        when(this.locationDao.getLocationById(any(UUID.class))).thenReturn(Optional.ofNullable(this.dummyLocation));
        when(this.wingService.getWingById(any(UUID.class))).thenReturn(this.dummyWing);
        when(this.locationDao.save(any(Location.class))).thenReturn(this.dummyLocation);

        Location result = this.SUT.editLocation(UUID.randomUUID(), locationEditDTO);

        assertNotNull(result);
        assertThat(result, is(dummyLocation));
    }

    @Test
    public void should_throw_not_found_exception_when_location_does_not_exist_edit() {
        LocationCreateEditDTO locationEditDTO = new LocationCreateEditDTO();

        when(this.locationDao.getLocationById(any(UUID.class))).thenThrow(new LocationNotFoundException("Location not found"));

        assertThrows(LocationNotFoundException.class, () -> {
            this.SUT.editLocation(dummyLocation.getId(), locationEditDTO);
        });
    }

    @Test
    public void should_throw_not_found_exception_when_wing_does_not_exist_edit() {
        LocationCreateEditDTO locationEditDTO = new LocationCreateEditDTO();

        when(this.locationDao.getLocationById(any(UUID.class))).thenThrow(new WingNotFoundException("Wing not found"));

        assertThrows(WingNotFoundException.class, () -> {
            this.SUT.editLocation(dummyLocation.getId(), locationEditDTO);
        });
    }

    @Test
    public void should_delete_location_when_it_exists() {
        when(locationDao.existsById(any(UUID.class))).thenReturn(true);

        SUT.remove(dummyLocation.getId());

        verify(locationDao, times(1)).remove(any(UUID.class));
    }

    @Test
    public void should_throw_not_found_error_when_location_does_not_exist_delete() {
        when(locationDao.existsById(any(UUID.class))).thenReturn(false);

        assertThrows(LocationNotFoundException.class, () -> {
            this.SUT.existsById(dummyLocation.getId());
        });
    }
}
