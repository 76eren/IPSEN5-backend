package com.cgi.ipsen5.Controller;

import com.cgi.ipsen5.Dao.ReservationHistoryDao;
import com.cgi.ipsen5.Dto.Report.RoomOccupancyResponseDTO;
import com.cgi.ipsen5.Model.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/reports")
@RequiredArgsConstructor
public class ReportController {
    private final ReservationHistoryDao reservationHistoryDao;

    @GetMapping("/occupancy")
    public ApiResponse<List<RoomOccupancyResponseDTO>> getRoomOccupancy(
            @RequestParam String buildingName,
            @RequestParam int year
    ) {
        List<RoomOccupancyResponseDTO> occupancyData = this.reservationHistoryDao.
                getRoomOccupancyByBuildingAndYear(buildingName, year);
        return new ApiResponse<>(occupancyData, HttpStatus.ACCEPTED);
    }
}
