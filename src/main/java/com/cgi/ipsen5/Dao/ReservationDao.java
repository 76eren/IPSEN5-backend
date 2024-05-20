package com.cgi.ipsen5.Dao;

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

    public Reservation save(Reservation reservation) {
        return this.reservationRepository.save(reservation);
    }

    public Reservation save(
            Location location,
            UUID userId,
            String startDateTime,
            String endDateTime,
            int numberOfPeople,
            String status
    ) {
        Optional<User> user = this.userDao.findById(userId);

        if (user.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }

        Reservation reservation = Reservation
                .builder()
                .id(UUID.randomUUID())
                .user(user.get())
                .location(location)
                .startDateTime(LocalDateTime.parse(startDateTime))
                .endDateTime(LocalDateTime.parse(endDateTime))
                .numberOfPeople(numberOfPeople)
                .createdAt(location.getCreatedAt())
                .status(status)
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
