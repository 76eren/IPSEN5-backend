package com.cgi.ipsen5.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.List;
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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;

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
