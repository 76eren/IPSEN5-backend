package com.cgi.ipsen5.Dao;

import com.cgi.ipsen5.Model.Reservation;
import com.cgi.ipsen5.Model.ReservationStatus;
import com.cgi.ipsen5.Model.User;
import com.cgi.ipsen5.Repository.ReservationRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Component
public class ReservationDao {

    private final ReservationRepository reservationRepository;
    private final ReservationHistoryDao reservationHistoryDAO;
    private final ReservationDeletionDao reservationDeletionDAO;

    public ReservationDao(ReservationRepository reservationRepository
            , ReservationHistoryDao reservationHistoryDAO,
                          ReservationDeletionDao reservationDeletionDAO) {
        this.reservationRepository = reservationRepository;
        this.reservationHistoryDAO = reservationHistoryDAO;
        this.reservationDeletionDAO = reservationDeletionDAO;
    }

    public boolean updateReservationStatus(LocalDateTime start, User userId){
        Optional<Reservation> optionalReservation = this.reservationRepository
                .getByStartDateTimeAndUser(start, userId);
        if (optionalReservation.isEmpty()) {
            return false;
        }

        Reservation reservation = optionalReservation.get();
        LocalDateTime reservationTime = reservation.getStartDateTime();
        LocalDateTime allowedLateTime = reservationTime.plusMinutes(15);

        if ((start.isBefore(reservationTime)
                || start.isEqual(reservationTime))
                && start.isAfter(allowedLateTime)) {
            return false;
        }

        reservation.setStatus(ReservationStatus.CHECKED_IN);
        this.reservationRepository.save(reservation);
        return true;
    }

    public Reservation createReservation(Reservation reservation){
        return this.reservationRepository.save(reservation);
    }

    public boolean cancelReservation(UUID id) {
        Optional<Reservation> optionalReservation = this.reservationRepository.getById(id);
        if (optionalReservation.isEmpty()) {
            return false;
        }

        Reservation reservation = optionalReservation.get();
        reservation.setStatus(ReservationStatus.CANCELLED);
        this.reservationRepository.save(reservation);
        boolean success = this.reservationHistoryDAO.saveReservationHistory(reservation);
        boolean deleted = this.reservationDeletionDAO.deleteReservation(id);
        return success && deleted;
    }
}
