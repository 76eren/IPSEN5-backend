package com.cgi.ipsen5.Service;

import com.cgi.ipsen5.Dao.LocationDao;
import com.cgi.ipsen5.Dao.ReservationDao;
import com.cgi.ipsen5.Dao.UserDao;
import com.cgi.ipsen5.Dto.Reservation.ReservationCreateDTO;
import com.cgi.ipsen5.Dto.Reservation.WorkplaceReservationDTO;
import com.cgi.ipsen5.Model.Location;
import com.cgi.ipsen5.Model.Reservation;
import com.cgi.ipsen5.Model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class ReservationService {
    private final ReservationDao reservationDao;
    private final LocationService locationService;
    private final UserDao userDao;

    public ReservationService(ReservationDao reservationDao,
                              UserDao userDao,
                              LocationService locationService) {
        this.reservationDao = reservationDao;
        this.userDao = userDao;
        this.locationService = locationService;
    }

    public Reservation saveWorkplaceReservation(WorkplaceReservationDTO reservationCreateDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID userId = UUID.fromString(authentication.getName());
        User user = this.userDao.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));

        List<Location> availableLocationsByWingId  = this.locationService.findAvailableLocationsByWingId(
                UUID.fromString(reservationCreateDTO.getWingId()),
                LocalDateTime.parse(reservationCreateDTO.getStartDateTime()),
                LocalDateTime.parse(reservationCreateDTO.getEndDateTime())
        );

        if (availableLocationsByWingId.isEmpty()
                || (reservationCreateDTO.getStartDateTime().equals(reservationCreateDTO.getEndDateTime()))
                || (LocalDateTime.parse(reservationCreateDTO.getStartDateTime()).isAfter(LocalDateTime.parse(reservationCreateDTO.getEndDateTime())))
        ){
            throw new RuntimeException("No available locations");
        }

        Random rand = new Random();
        int randomIndex = rand.nextInt(availableLocationsByWingId.size());

        Location randomLocation = availableLocationsByWingId.get(randomIndex);

        Reservation reservation = Reservation.builder()
                .user(user)
                .location(randomLocation)
                .startDateTime(LocalDateTime.parse(reservationCreateDTO.getStartDateTime()))
                .endDateTime(LocalDateTime.parse(reservationCreateDTO.getEndDateTime()))
                .numberOfPeople(reservationCreateDTO.getNumberOfPeople())
                .build();
        return this.reservationDao.saveWorkplaceReservation(reservation);
    }
}
