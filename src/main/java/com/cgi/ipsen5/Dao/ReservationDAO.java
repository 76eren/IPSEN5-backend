package com.cgi.ipsen5.Dao;

import com.cgi.ipsen5.Model.Reservation;
import com.cgi.ipsen5.Model.ReservationStatus;
import com.cgi.ipsen5.Model.User;
import com.cgi.ipsen5.Repository.ReservationRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class ReservationDAO {

    private final ReservationRepository reservationRepository;

    public ReservationDAO(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public boolean updateReservationStatus(LocalDateTime start, User userId){
    Optional<Reservation> optionalReservation = this.reservationRepository
            .getByStartDateTimeAndUser(start, userId);
        if (optionalReservation.isPresent()){
            Reservation reservation = optionalReservation.get();
            LocalDateTime reservationTime = reservation.getStartDateTime();
            LocalDateTime reservationTimePlus15Min = reservationTime.plusMinutes(15);

            if ((start.isBefore(reservationTime)
                    || start.isEqual(reservationTime))
                    && start.isAfter(reservationTimePlus15Min)) {
                reservation.setStatus(ReservationStatus.CHECKED_IN);
                this.reservationRepository.save(reservation);
                return true;
            }
        }
        return false;
    }

    public Reservation createReservation(Reservation reservation){
        return this.reservationRepository.save(reservation);
    }

}
