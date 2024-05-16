package com.cgi.ipsen5.Dto.Reservation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationCreateDTO {
    private String locationId;
    private String startDateTime;
    private String endDateTime;
    private int numberOfPeople;
    private String status;
}


