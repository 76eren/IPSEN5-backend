package com.cgi.ipsen5.Dao;

import com.cgi.ipsen5.Model.Reservation;
import com.cgi.ipsen5.Repository.ReservationRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Component
public class ReservationDeletionDAO {

    private final ReservationRepository reservationRepository;

    public ReservationDeletionDAO(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Transactional
    public boolean deleteReservation(UUID id) {
        Optional<Reservation> optionalReservation = this.reservationRepository.getById(id);

        if (optionalReservation.isEmpty()) {
            return false;
        }

        this.reservationRepository.deleteById(id);
        return true;
    }
}