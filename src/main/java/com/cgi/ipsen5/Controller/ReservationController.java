package com.cgi.ipsen5.Controller;

import com.cgi.ipsen5.Dao.LocationDao;
import com.cgi.ipsen5.Dao.ReservationDao;
import com.cgi.ipsen5.Dto.Reservation.ReservationCreateDTO;
import com.cgi.ipsen5.Dto.Reservation.ReservationResponseDTO;
import com.cgi.ipsen5.Mapper.ReservationMapper;
import com.cgi.ipsen5.Model.ApiResponse;
import com.cgi.ipsen5.Model.Location;
import com.cgi.ipsen5.Model.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/reservation")
@RequiredArgsConstructor
public class ReservationController {
    private final LocationDao locationDao;
    private final ReservationDao reservationDao;
    private final ReservationMapper reservationMapper;

    @PostMapping()
    public ApiResponse<ReservationResponseDTO> createReservation(@RequestBody ReservationCreateDTO reservationCreateDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UUID id = UUID.fromString(authentication.getName());

        Location location = locationDao.findLocationById(UUID.fromString(reservationCreateDTO.getLocationId()));
        Reservation reservation = reservationDao.save(
                location,
                id,
                reservationCreateDTO.getStartDateTime(),
                reservationCreateDTO.getEndDateTime(),
                reservationCreateDTO.getNumberOfPeople(),
                reservationCreateDTO.getStatus()
        );


        return new ApiResponse<>(this.reservationMapper.fromEntity(reservation));
    }

    @GetMapping("/{id}")
    public ApiResponse<ReservationResponseDTO> getReservationById(@PathVariable String id) {
        Optional<Reservation> reservation = this.reservationDao.findById(UUID.fromString(id));

        return reservation
                .map(value -> new ApiResponse<>(this.reservationMapper.fromEntity(value)))
                .orElseGet(() -> new ApiResponse<>("Reservation not found", HttpStatus.NOT_FOUND));

    }


    @GetMapping()
    public ApiResponse<List<Reservation>> getAllReservations() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID id = UUID.fromString(authentication.getName());

        return new ApiResponse<>(this.reservationDao.findAll(id)); // We return the model and not the DTO so the response will contain the ID of the reservation
    }


}

