package com.cgi.ipsen5.Service;

import com.cgi.ipsen5.Dao.ReservationDao;
import com.cgi.ipsen5.Dto.Reservation.RoomReservationDTO;
import com.cgi.ipsen5.Dto.Reservation.WorkplaceReservationDTO;
import com.cgi.ipsen5.Exception.LocationNotFoundException;
import com.cgi.ipsen5.Exception.ReservationErrorExecption;
import com.cgi.ipsen5.Model.Location;
import com.cgi.ipsen5.Model.Reservation;
import com.cgi.ipsen5.Model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceUnitTest {

    @Mock
    private ReservationDao reservationDao;
    @Mock
    private LocationService locationService;
    @Mock
    private UserService userService;
    @Mock
    private WingService wingService;

    private ReservationService SUT;

    private WorkplaceReservationDTO dummyWorkplaceReservationDTO;
    private User dummyUser;
    private Location dummyLocation;
    private Reservation dummyReservation;
    private RoomReservationDTO dummyRoomReservationDTO;


    @BeforeEach
    public void setUp() {
        this.SUT = new ReservationService(reservationDao, locationService, userService, wingService);
        this.dummyUser = new User();
        this.dummyLocation = new Location();
        this.dummyReservation = new Reservation();
        this.dummyWorkplaceReservationDTO = new WorkplaceReservationDTO();
        dummyWorkplaceReservationDTO.setWingId(UUID.randomUUID().toString());
        dummyWorkplaceReservationDTO.setStartDateTime(LocalDateTime.now().toString());
        dummyWorkplaceReservationDTO.setEndDateTime(LocalDateTime.now().plusHours(1).toString());
        this.dummyRoomReservationDTO = new RoomReservationDTO();
        dummyRoomReservationDTO.setLocationId(UUID.randomUUID().toString());
        dummyRoomReservationDTO.setStartDateTime(LocalDateTime.now().toString());
        dummyRoomReservationDTO.setEndDateTime(LocalDateTime.now().plusHours(1).toString());
        dummyRoomReservationDTO.setNumberOfAttendees(5);
    }

    @Test
    public void should_save_workplace_reservation() {
        doNothing().when(this.wingService).wingExistsById(any(UUID.class));
        when(this.userService.getUserFromAuthContext()).thenReturn(dummyUser);
        when(this.locationService.findAvailableLocationsByWingId(any(UUID.class), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Collections.singletonList(dummyLocation));
        when(this.locationService.getRandomAvailableLocationIndex(anyList())).thenReturn(dummyLocation);
        when(this.reservationDao.saveWorkplaceReservation(any(Reservation.class))).thenReturn(dummyReservation);

        Reservation result = this.SUT.saveWorkplaceReservation(dummyWorkplaceReservationDTO);

        assertNotNull(result);
        assertEquals(dummyReservation, result);
    }

    @Test
    public void should_throw_exception_when_no_available_locations_for_workplace_reservation() {
        doNothing().when(this.wingService).wingExistsById(any(UUID.class));
        when(this.userService.getUserFromAuthContext()).thenReturn(dummyUser);
        when(this.locationService.findAvailableLocationsByWingId(any(UUID.class), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Collections.emptyList());

        assertThrows(ReservationErrorExecption.class, () -> {
            this.SUT.saveWorkplaceReservation(dummyWorkplaceReservationDTO);
        });
    }

    @Test
    public void should_save_room_reservation() {
        doNothing().when(this.locationService).existsById(any(UUID.class));
        doNothing().when(this.locationService).isLocationRoom(any(UUID.class));
        doNothing().when(this.locationService).isRoomAvailable(any(UUID.class), any(LocalDateTime.class), any(LocalDateTime.class));
        when(this.userService.getUserFromAuthContext()).thenReturn(dummyUser);
        when(this.locationService.getLocationById(any(UUID.class))).thenReturn(dummyLocation);
        when(this.reservationDao.saveRoomReservation(any(Reservation.class))).thenReturn(dummyReservation);

        Reservation result = this.SUT.saveRoomReservation(dummyRoomReservationDTO);

        assertNotNull(result);
        assertEquals(dummyReservation, result);
    }

    @Test
    public void should_throw_exception_when_room_not_available_for_reservation() {
        lenient().doThrow(new LocationNotFoundException("Room not available"))
                .when(this.locationService).isRoomAvailable(any(UUID.class), any(LocalDateTime.class), any(LocalDateTime.class));

        assertThrows(LocationNotFoundException.class, () -> {
            this.SUT.saveRoomReservation(dummyRoomReservationDTO);
        });
    }
}