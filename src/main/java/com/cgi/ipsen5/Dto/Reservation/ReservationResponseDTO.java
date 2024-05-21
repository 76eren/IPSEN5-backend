package com.cgi.ipsen5.Dto.Reservation;


import com.cgi.ipsen5.Dto.Reservation.Location.LocationDTO;
import com.cgi.ipsen5.Dto.User.UserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationResponseDTO {
    private UserResponseDTO user;
    private LocationDTO location;
    private String status;
    private String startDateTime;
    private String endDateTime;
    private int numberOfPeople;
        private String createdAt;
}
