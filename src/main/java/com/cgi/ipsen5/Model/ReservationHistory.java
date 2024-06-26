package com.cgi.ipsen5.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "reservation_history")
public class ReservationHistory {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(nullable = false, unique = true)
    @JsonProperty
    private UUID id;

    private UUID oldReservationId;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;

    @Column(name = "status")
    @JsonProperty
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ReservationStatus status = ReservationStatus.NOT_CHECKED_IN;

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
