package com.cgi.ipsen5.Dao;

import com.cgi.ipsen5.Dto.Reservation.ReservationCreateDTO;
import com.cgi.ipsen5.Model.Location;
import com.cgi.ipsen5.Model.Reservation;
import com.cgi.ipsen5.Model.ReservationStatus;
import com.cgi.ipsen5.Model.User;
import com.cgi.ipsen5.Repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ReservationDao {
    private final ReservationRepository reservationRepository;
    private final UserDao userDao;
    private final LocationDao locationDao;
    private final ReservationHistoryDao reservationHistoryDAO;
    private final ReservationDeletionDao reservationDeletionDAO;

    //TODO: Later verwijderen als het reserveren van lokalen ook lukt
    public Reservation save(Reservation reservation) {
        return this.reservationRepository.save(reservation);
    }

    //TODO: Later verwijderen als het reservere van lokalen ook lukt
    public Reservation save(UUID userId, ReservationCreateDTO reservationCreateDTO) {
        Optional<User> user = this.userDao.findById(userId);

        if (user.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }

        Location location = locationDao.getLocationById(UUID.fromString(reservationCreateDTO.getLocationId()));
        // TODO: Check if location exists

        Reservation newReservation = Reservation
                .builder()
                .user(user.get())
                .location(location)
                .startDateTime(LocalDateTime.parse(reservationCreateDTO.getStartDateTime()))
                .endDateTime(LocalDateTime.parse(reservationCreateDTO.getEndDateTime()))
                .numberOfPeople(reservationCreateDTO.getNumberOfPeople())
                .createdAt(location.getCreatedAt())
                .build();

        return this.reservationRepository.save(newReservation);
    }

    public Reservation saveWorkplaceReservation(Reservation reservation){
        return this.reservationRepository.save(reservation);
    }

    public Reservation saveRoomReservation(Reservation reservation){
        return this.reservationRepository.save(reservation);
    }

    public List<Reservation> findReservationsBetween(
            Location location,
            LocalDateTime startDateTime,
            LocalDateTime endDateTime) {
        return this.reservationRepository
                .findByLocationAndStartDateTimeLessThanAndEndDateTimeGreaterThan(
                        location,
                        endDateTime,
                        startDateTime
                );
    }

    public boolean updateReservationStatus(LocalDateTime start, User userId) {
        Optional<Reservation> optionalReservation = this.reservationRepository
                .getByStartDateTimeAndUser(start, userId);
        if (optionalReservation.isEmpty()) {
            return false;
        }

        Reservation reservation = optionalReservation.get();
        LocalDateTime reservationTime = reservation.getStartDateTime();
        LocalDateTime allowedLateTime = reservationTime.plusMinutes(15);

        if (start.isBefore(allowedLateTime) && (start.isBefore(reservationTime) || start.isEqual(reservationTime))) {
            reservation.setStatus(ReservationStatus.CHECKED_IN);
            this.reservationRepository.save(reservation);
            return true;
        }

        return false;
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

    public Optional<Reservation> findById(UUID id) {
        return this.reservationRepository.getById(id);
    }

    public List<Reservation> findAll(UUID userId) {
        // TODO: check if user exists first
        return this.reservationRepository.findReservationByUserId(userId);
    }
}