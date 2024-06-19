package com.cgi.ipsen5.Controller;

import com.cgi.ipsen5.Dto.Report.NoShowResponseDTO;
import com.cgi.ipsen5.Dto.Report.RoomOccupancyResponseDTO;
import com.cgi.ipsen5.Exception.BuildingNotFoundException;
import com.cgi.ipsen5.Model.ApiResponse;
import com.cgi.ipsen5.Service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/reports")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/occupancy")
    public ApiResponse<List<RoomOccupancyResponseDTO>> getRoomOccupancy(
            @RequestParam String buildingName,
            @RequestParam int year
    ) {
        List<RoomOccupancyResponseDTO> occupancyData = this.reportService
                .getRoomOccupancyByBuildingAndYear(buildingName, year);
        return new ApiResponse<>(occupancyData, HttpStatus.OK);
    }

    @GetMapping("/noshow")
    public ApiResponse<List<NoShowResponseDTO>> getNoShows(
            @RequestParam String buildingName,
            @RequestParam int year
    ){
        List<NoShowResponseDTO> noShowData = this.reportService
                .getNoShowsByBuildingAndYear(buildingName, year);
        return new ApiResponse<>(noShowData,HttpStatus.OK);
    }

    @ExceptionHandler({BuildingNotFoundException.class})
    public ApiResponse<String> handleException(Exception e) {
        return new ApiResponse<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
