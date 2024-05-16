package com.cgi.ipsen5.Mapper;

import com.cgi.ipsen5.Dto.Reservation.Location.LocationDTO;
import com.cgi.ipsen5.Dto.Reservation.ReservationResponseDTO;
import com.cgi.ipsen5.Model.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationMapper {
    private final LocationMapper locationMapper;
    private final UserMapper userMapper;

    public ReservationResponseDTO fromEntity(Reservation reservation) {

        return ReservationResponseDTO
                .builder()
                .user(this.userMapper.fromEntity(reservation.getUser()))
                .location(this.locationMapper.fromEntity(reservation.getLocation()))
                .status(reservation.getStatus())
                .startDateTime(reservation.getStartDateTime().toString())
                .endDateTime(reservation.getEndDateTime().toString())
                .numberOfPeople(reservation.getNumberOfPeople())
                .createdAt(reservation.getCreatedAt().toString())
                .build();

    }
}
