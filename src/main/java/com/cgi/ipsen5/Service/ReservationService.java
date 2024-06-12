package com.cgi.ipsen5.Service;

import com.cgi.ipsen5.Dao.ReservationDao;
import com.cgi.ipsen5.Dto.Reservation.RoomReservationDTO;
import com.cgi.ipsen5.Dto.Reservation.WorkplaceReservationDTO;
import com.cgi.ipsen5.Exception.ReservationErrorExecption;
import com.cgi.ipsen5.Model.Location;
import com.cgi.ipsen5.Model.Reservation;
import com.cgi.ipsen5.Model.ReservationHistory;
import com.cgi.ipsen5.Model.User;
import com.cgi.ipsen5.Repository.ReservationHistoryRepository;
import com.cgi.ipsen5.Repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ReservationService {
    private final ReservationDao reservationDao;
    private final LocationService locationService;
    private final UserService userService;
    private final WingService wingService;
    private final ReservationRepository reservationRepository;
    private final ReservationHistoryRepository reservationHistoryRepository;

    public Reservation saveWorkplaceReservation(WorkplaceReservationDTO reservationCreateDTO) {
        this.wingService.wingExistsById(UUID.fromString(reservationCreateDTO.getWingId()));
        User user = this.userService.getUserFromAuthContext();
        List<Location> availableLocationsByWingId  = this.locationService.findAvailableLocationsByWingId(
                UUID.fromString(reservationCreateDTO.getWingId()),
                LocalDateTime.parse(reservationCreateDTO.getStartDateTime()),
                LocalDateTime.parse(reservationCreateDTO.getEndDateTime())
        );

        if (availableLocationsByWingId.isEmpty()) {
            throw new ReservationErrorExecption("No available locations");
        }
        this.validateWorkplaceReservation(reservationCreateDTO);

        Location randomLocation = this.locationService.getRandomAvailableLocationIndex(availableLocationsByWingId);

        Reservation reservation = Reservation.builder()
                .user(user)
                .location(randomLocation)
                .startDateTime(LocalDateTime.parse(reservationCreateDTO.getStartDateTime()))
                .endDateTime(LocalDateTime.parse(reservationCreateDTO.getEndDateTime()))
                .numberOfPeople(1)
                .build();
        return this.reservationDao.saveWorkplaceReservation(reservation);
    }

    public Reservation saveRoomReservation(RoomReservationDTO roomReservation){
        this.locationService.existsById(UUID.fromString(roomReservation.getLocationId()));
        this.locationService.isLocationRoom(UUID.fromString(roomReservation.getLocationId()));
        this.locationService.isRoomAvailable(
                UUID.fromString(roomReservation.getLocationId()),
                LocalDateTime.parse(roomReservation.getStartDateTime()),
                LocalDateTime.parse(roomReservation.getEndDateTime()));
        User user = this.userService.getUserFromAuthContext();
        this.validateRoomReservation(roomReservation);
        Reservation reservation = Reservation.builder()
                .user(user)
                .location(this.locationService.getLocationById(UUID.fromString(roomReservation.getLocationId())))
                .startDateTime(LocalDateTime.parse(roomReservation.getStartDateTime()))
                .endDateTime(LocalDateTime.parse(roomReservation.getEndDateTime()))
                .numberOfPeople(roomReservation.getNumberOfAttendees())
                .build();
        return this.reservationDao.saveRoomReservation(reservation);
    }

    private void validateWorkplaceReservation(WorkplaceReservationDTO reservationCreateDTO) {
        if (reservationCreateDTO.getStartDateTime().equals(reservationCreateDTO.getEndDateTime())
                || LocalDateTime.parse(reservationCreateDTO.getStartDateTime()).isAfter(LocalDateTime.parse(reservationCreateDTO.getEndDateTime()))) {
            throw new ReservationErrorExecption("Invalid date time");
        }
    }
    private void validateRoomReservation(RoomReservationDTO reservationCreateDTO) {
        if (reservationCreateDTO.getStartDateTime().equals(reservationCreateDTO.getEndDateTime())
                || LocalDateTime.parse(reservationCreateDTO.getStartDateTime()).isAfter(LocalDateTime.parse(reservationCreateDTO.getEndDateTime()))) {
            throw new ReservationErrorExecption("Invalid date time");
        }
    }

    public void moveOldReservationsToHistory(LocalDateTime currentTime) {
        List<Reservation> oldReservations = reservationRepository.findAllByEndDateTimeBefore(currentTime);

        for (Reservation reservation : oldReservations) {
            ReservationHistory history = new ReservationHistory();
            history.setOldReservationId(reservation.getId());
            history.setUser(reservation.getUser());
            history.setLocation(reservation.getLocation());
            history.setStatus(reservation.getStatus());
            history.setStartDateTime(reservation.getStartDateTime());
            history.setEndDateTime(reservation.getEndDateTime());
            history.setNumberOfPeople(reservation.getNumberOfPeople());
            history.setCreatedAt(reservation.getCreatedAt());

            reservationHistoryRepository.save(history);
//            reservationRepository.delete(reservation);
        }
    }
}
