package com.cgi.ipsen5.Dao;

import com.cgi.ipsen5.Dto.Report.RoomOccupancyResponseDTO;
import com.cgi.ipsen5.Model.Reservation;
import com.cgi.ipsen5.Model.ReservationHistory;
import com.cgi.ipsen5.Repository.ReservationHistoryRepository;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class ReservationHistoryDao {
    private final ReservationHistoryRepository reservationHistoryRepository;

    public ReservationHistoryDao(ReservationHistoryRepository reservationHistoryRepository) {
        this.reservationHistoryRepository = reservationHistoryRepository;
    }

    public boolean saveReservationHistory(Reservation reservation) {
        ReservationHistory reservationHistory = ReservationHistory.builder()
                .oldReservationId(reservation.getId())
                .user(reservation.getUser())
                .location(reservation.getLocation())
                .status(reservation.getStatus())
                .startDateTime(reservation.getStartDateTime())
                .endDateTime(reservation.getEndDateTime())
                .numberOfPeople(reservation.getNumberOfPeople())
                .createdAt(reservation.getCreatedAt())
                .build();
        this.reservationHistoryRepository.save(reservationHistory);
        return true;
    }

    public List<RoomOccupancyResponseDTO> getRoomOccupancyByBuildingAndYear(String buildingName, int year) {
        return this.reservationHistoryRepository.findRoomOccupancyByBuildingAndYear(buildingName, year);
    }
}