package com.cgi.ipsen5.Repository;

import com.cgi.ipsen5.Model.Reservation;
import com.cgi.ipsen5.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long>{
    Optional<Reservation> getByStartDateTimeAndUser(LocalDateTime startDateTime, User user);
}
