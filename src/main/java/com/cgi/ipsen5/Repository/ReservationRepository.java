package com.cgi.ipsen5.Repository;

import com.cgi.ipsen5.Model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ReservationRepository extends JpaRepository<Reservation, UUID> {
    List<Reservation> findReservationByUserId(UUID userId);
}
