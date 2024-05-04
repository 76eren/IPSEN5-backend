package com.cgi.ipsen5.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "reservation")
public class Reservation {
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
    private UUID userId;
    private UUID locationId;

    @Column(name = "status")
    @JsonProperty
    private String status;

    @Column(name = "start_date_time")
    @JsonProperty
    private LocalDateTime startDateTime;

    @Column(name = "end_date_time")
    @JsonProperty
    private LocalDateTime endDateTime;

    @Column(name = "number_of_people")
    @JsonProperty
    private int numberOfPeople;

    @Column(name = "created_at")
    @JsonProperty
    private LocalDateTime createdAt;
}
