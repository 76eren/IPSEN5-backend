package com.cgi.ipsen5.Model;

import java.util.UUID;

public class ReservationRequest {
    private UUID id;
    //FKs
    private UUID reservationId;
    private UUID requesterId;
    private UUID ownerId;

    private String message;
    private String status;
}
