package com.cgi.ipsen5.Service;

import com.cgi.ipsen5.Dao.ReservationDao;
import com.cgi.ipsen5.Dto.Reservation.WorkplaceReservationDTO;
import com.cgi.ipsen5.Exception.ReservationErrorExecption;
import com.cgi.ipsen5.Model.Location;
import com.cgi.ipsen5.Model.Reservation;
import com.cgi.ipsen5.Model.User;
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

    public Reservation saveWorkplaceReservation(WorkplaceReservationDTO reservationCreateDTO) {
        User user = this.userService.getUserFromAuthContext();

        List<Location> availableLocationsByWingId  = this.locationService.findAvailableLocationsByWingId(
                UUID.fromString(reservationCreateDTO.getWingId()),
                LocalDateTime.parse(reservationCreateDTO.getStartDateTime()),
                LocalDateTime.parse(reservationCreateDTO.getEndDateTime())
        );

        if (availableLocationsByWingId.isEmpty()) {
            throw new ReservationErrorExecption("No available locations");
        }
        this.validateReservation(reservationCreateDTO);

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

    private void validateReservation(WorkplaceReservationDTO reservationCreateDTO) {
        if (reservationCreateDTO.getStartDateTime().equals(reservationCreateDTO.getEndDateTime())
                || LocalDateTime.parse(reservationCreateDTO.getStartDateTime()).isAfter(LocalDateTime.parse(reservationCreateDTO.getEndDateTime()))) {
            throw new ReservationErrorExecption("Invalid date time");
        }
    }

}
