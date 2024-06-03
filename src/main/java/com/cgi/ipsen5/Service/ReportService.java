package com.cgi.ipsen5.Service;

import com.cgi.ipsen5.Dao.ReservationHistoryDao;
import com.cgi.ipsen5.Dto.Report.NoShowResponseDTO;
import com.cgi.ipsen5.Dto.Report.RoomOccupancyResponseDTO;
import com.cgi.ipsen5.Model.Building;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ReportService {
    private final ReservationHistoryDao reservationHistoryDao;
    private final BuildingService buildingService;

    public ReportService(ReservationHistoryDao reservationHistoryDao, BuildingService buildingService) {
        this.reservationHistoryDao = reservationHistoryDao;
        this.buildingService = buildingService;
    }

    public List<RoomOccupancyResponseDTO> getRoomOccupancyByBuildingAndYear(String buildingName, int year) {
       Building building = this.buildingService.getBuildingByName(buildingName);
       return this.reservationHistoryDao.getRoomOccupancyByBuildingAndYear(building.getId(), year);
    }

    public List<NoShowResponseDTO> getNoShowsByBuildingAndYear(String buildingName, int year) {
        Building building = this.buildingService.getBuildingByName(buildingName);
        return this.reservationHistoryDao.getNoShowsByBuildingAndYear(building.getId(), year);
    }


}
