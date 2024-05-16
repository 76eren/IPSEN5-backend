package com.cgi.ipsen5.Controller;

import com.cgi.ipsen5.Dao.LocationDao;
import com.cgi.ipsen5.Dao.ReservationDao;
import com.cgi.ipsen5.Dto.Reservation.ReservationCreateDTO;
import com.cgi.ipsen5.Mapper.ReservationMapper;
import com.cgi.ipsen5.Model.ApiResponse;
import com.cgi.ipsen5.Model.Location;
import com.cgi.ipsen5.Model.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/reservation/")
@RequiredArgsConstructor
public class ReservationController {
    private final LocationDao locationDao;
    private final ReservationDao reservationDao;
    private final ReservationMapper reservationMapper;

    @PostMapping("/{id}")
    public ApiResponse<?> reserve(@RequestBody ReservationCreateDTO reservationCreateDTO, @PathVariable("id") UUID id) {
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


}

