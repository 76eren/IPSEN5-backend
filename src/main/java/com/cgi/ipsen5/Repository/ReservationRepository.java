package com.cgi.ipsen5.Repository;

import com.cgi.ipsen5.Model.Reservation;
import com.cgi.ipsen5.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findReservationByUserId(UUID userId);
    Optional<Reservation> getByStartDateTimeAndUser(LocalDateTime startDateTime, User user);
    Optional<Reservation> getById(UUID id);
    void deleteById(UUID id);
}
