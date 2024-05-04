package com.cgi.ipsen5.Model;

import java.time.LocalDateTime;
import java.util.UUID;

public class ReservationHistory {
    private UUID id;
    //FKs
    private UUID userId;
    private UUID locationId;

    private String status;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private int numberOfPeople;
    private LocalDateTime createdAt;
}
