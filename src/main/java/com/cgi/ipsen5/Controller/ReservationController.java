package com.cgi.ipsen5.Controller;

import com.cgi.ipsen5.Dao.ReservationDao;
import com.cgi.ipsen5.Dto.Reservation.ReservationCreateDTO;
import com.cgi.ipsen5.Dto.Reservation.ReservationResponseDTO;
import com.cgi.ipsen5.Dto.Reservation.WorkplaceReservationDTO;
import com.cgi.ipsen5.Mapper.ReservationMapper;
import com.cgi.ipsen5.Model.ApiResponse;
import com.cgi.ipsen5.Model.Reservation;
import com.cgi.ipsen5.Model.ReservationUpdateRequest;
import com.cgi.ipsen5.Service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/reservations")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationDao reservationDao;
    private final ReservationMapper reservationMapper;
    private final ReservationService reservationService;

    @PostMapping("/reserve-workplace")
    public ApiResponse<String> reserveWorkplace(@RequestBody WorkplaceReservationDTO reservationCreateDTO) {
        Reservation reservation = this.reservationService.saveWorkplaceReservation(reservationCreateDTO);
        if (reservation == null) {
            return new ApiResponse<>("Could not reserve workplace", HttpStatus.NOT_FOUND);
        }
        return new ApiResponse<>("Reservation created successfully", HttpStatus.ACCEPTED);
    }

    @PostMapping("/reserve-room")
    public ApiResponse<String> reserveRoom(){
        return new ApiResponse<>("");
    }

    @PostMapping("/create")
    public ApiResponse<ReservationResponseDTO> createReservation(@RequestBody ReservationCreateDTO reservationCreateDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID id = UUID.fromString(authentication.getName());
        Reservation reservation = reservationDao.save(id, reservationCreateDTO);
        return new ApiResponse<>(this.reservationMapper.fromEntity(reservation));
    }

    @GetMapping("/{id}")
    public ApiResponse<ReservationResponseDTO> getReservationById(@PathVariable String id) {
        Optional<Reservation> reservation = this.reservationDao.findById(UUID.fromString(id));
        return reservation
                .map(value -> new ApiResponse<>(this.reservationMapper.fromEntity(value)))
                .orElseGet(() -> new ApiResponse<>("Reservation not found", HttpStatus.NOT_FOUND));
    }

    @GetMapping("/all")
    public ApiResponse<List<Reservation>> getAllReservations() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID id = UUID.fromString(authentication.getName());
        return new ApiResponse<>(this.reservationDao.findAll(id)); // We return the model and not the DTO so the response will contain the ID of the reservation
    }

    @PostMapping(value = "/check-in")
    public ApiResponse<String> updateReservationStatus(
            @RequestBody ReservationUpdateRequest reservationUpdateRequest
    ) {
        boolean success = this.reservationDao.updateReservationStatus(
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
        boolean success = this.reservationDao.cancelReservation(id);
        if (!success) {
            return new ApiResponse<>("Could not cancel reservation.", HttpStatus.NOT_FOUND);
        }
        return new ApiResponse<>("Reservation cancelled successfully.", HttpStatus.ACCEPTED);
    }
}
