package com.cgi.ipsen5.Dao;

import com.cgi.ipsen5.Dto.Reservation.ReservationCreateDTO;
import com.cgi.ipsen5.Model.Location;
import com.cgi.ipsen5.Model.Reservation;
import com.cgi.ipsen5.Model.User;
import com.cgi.ipsen5.Repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ReservationDao {
    private final ReservationRepository reservationRepository;
    private final UserDao userDao;
    private final LocationDao locationDao;

    public Reservation save(Reservation reservation) {
        return this.reservationRepository.save(reservation);
    }

    public Reservation save(UUID userId, ReservationCreateDTO reservationCreateDTO) {
        Optional<User> user = this.userDao.findById(userId);

        if (user.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }

        Location location = locationDao.findLocationById(UUID.fromString(reservationCreateDTO.getLocationId()));


        Reservation reservation = Reservation
                .builder()
                .user(user.get())
                .location(location)
                .startDateTime(LocalDateTime.parse(reservationCreateDTO.getStartDateTime()))
                .endDateTime(LocalDateTime.parse(reservationCreateDTO.getEndDateTime()))
                .numberOfPeople(reservationCreateDTO.getNumberOfPeople())
                .createdAt(location.getCreatedAt())
                .build();

        return this.reservationRepository.save(reservation);

    }

    public Optional<Reservation> findById(UUID id) {
        return this.reservationRepository.findById(id);
    }

    public List<Reservation> findAll(UUID id) {
        List<Reservation> reservations = this.reservationRepository.findAll();
        List<Reservation> reservationsFromId = new ArrayList<>();
        for (Reservation reservation : reservations) {
            if (reservation.getUser().getId().equals(id)) {
                reservationsFromId.add(reservation);
            }
        }

        return reservationsFromId;
    }
}
