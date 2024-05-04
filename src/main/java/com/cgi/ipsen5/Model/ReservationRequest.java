package com.cgi.ipsen5.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "reservation_request")
public class ReservationRequest {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(nullable = false, unique = true)
    @JsonProperty
    private UUID id;
    //FKs
    private UUID reservationId;
    private UUID requesterId;
    private UUID ownerId;

    @Column(name = "message")
    @JsonProperty
    private String message;

    @Column(name = "status")
    @JsonProperty
    private String status;
}
