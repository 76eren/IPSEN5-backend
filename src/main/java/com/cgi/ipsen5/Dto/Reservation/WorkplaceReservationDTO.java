package com.cgi.ipsen5.Dto.Reservation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkplaceReservationDTO {
    private String wingId;
    private String startDateTime; // Should be in the format like "2024-05-11T14:30:00"
    private String endDateTime; // Should be in the format like "2024-05-11T14:30:00"
    private int numberOfPeople;
    private String status;
}
