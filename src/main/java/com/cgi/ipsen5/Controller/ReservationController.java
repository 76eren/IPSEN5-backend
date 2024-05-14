package com.cgi.ipsen5.Controller;

import com.cgi.ipsen5.Dao.ReservationDAO;
import com.cgi.ipsen5.Model.ApiResponse;
import com.cgi.ipsen5.Model.ReservationUpdateRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;


@RestController
@RequestMapping(value = "/api/v1/reservation")
public class ReservationController {

    private final ReservationDAO reservationDAO;

    public ReservationController(ReservationDAO reservationDAO) {
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
}
