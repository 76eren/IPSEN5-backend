package com.cgi.ipsen5.Controller;

import com.cgi.ipsen5.Dao.ReservationDao;
import com.cgi.ipsen5.Model.ApiResponse;
import com.cgi.ipsen5.Model.ReservationUpdateRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.UUID;


@RestController
@RequestMapping(value = "/api/v1/reservation")
public class ReservationController {

    private final ReservationDao reservationDAO;

    public ReservationController(ReservationDao reservationDAO) {
        this.reservationDAO = reservationDAO;
    }

    @PostMapping(value = "/check-in")
    public ApiResponse<String> updateReservationStatus(
            @RequestBody ReservationUpdateRequest reservationUpdateRequest
            ) {
        boolean success = this.reservationDAO.updateReservationStatus(
                reservationUpdateRequest.getTimeStamp(),
                reservationUpdateRequest.getUser());
        if (success) {
            return new ApiResponse<>("Status of reservation updated to CHECKED IN successfully.", HttpStatus.ACCEPTED);
        } else {
            return new ApiResponse<>("Could not update status of reservation.", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/{id}/cancel")
    public ApiResponse<String> cancelReservation(@PathVariable UUID id) {
        boolean success = this.reservationDAO.cancelReservation(id);
        if (!success) {
            return new ApiResponse<>("Could not cancel reservation.", HttpStatus.NOT_FOUND);
        }
        return new ApiResponse<>("Reservation cancelled successfully.", HttpStatus.ACCEPTED);
    }
}
