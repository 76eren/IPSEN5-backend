package com.cgi.ipsen5.Repository;

import com.cgi.ipsen5.Dto.Report.NoShowResponseDTO;
import com.cgi.ipsen5.Dto.Report.RoomOccupancyResponseDTO;
import com.cgi.ipsen5.Model.ReservationHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReservationHistoryRepository extends JpaRepository<ReservationHistory, Long> {
    @Query(value = "SELECT l.name AS room, COUNT(*) AS numberOfUsages, rh.start_date_time AS date " +
            "FROM reservation_history rh " +
            "JOIN location l ON rh.location_id = l.id " +
            "JOIN wing w ON l.wing_id = w.id " +
            "JOIN floor f ON w.floor_id = f.id " +
            "JOIN building b ON f.building_id = b.id " +
            "WHERE b.name = :buildingName AND EXTRACT(YEAR FROM rh.start_date_time) = :year " +
            "GROUP BY l.id, rh.start_date_time, l.name",
            nativeQuery = true)
    List<RoomOccupancyResponseDTO> findRoomOccupancyByBuildingAndYear(
            @Param("buildingName") String buildingName,
            @Param("year") int year);

    @Query(value = "SELECT CONCAT(e.first_name, ' ', e.last_name) AS employeeName, COUNT(*) AS numberOfReservations, " +
            "SUM(CASE WHEN rh.status = 'NOT_CHECKED_IN' THEN 1 ELSE 0 END) AS numberOfNoShows " +
            "FROM reservation_history rh " +
            "JOIN employee e ON rh.user_id = e.id " +
            "JOIN location l ON rh.location_id = l.id " +
            "JOIN wing w ON l.wing_id = w.id " +
            "JOIN floor f ON w.floor_id = f.id " +
            "JOIN building b ON f.building_id = b.id " +
            "WHERE b.name = :buildingName AND EXTRACT(YEAR FROM rh.start_date_time) = :year " +
            "GROUP BY employeeName", nativeQuery = true)
    List<NoShowResponseDTO> findNoShowsByBuildingAndYear(
            @Param("buildingName") String buildingName,
            @Param("year") int year);
}
