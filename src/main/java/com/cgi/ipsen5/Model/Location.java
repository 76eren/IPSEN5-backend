package com.cgi.ipsen5.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "location")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(nullable = false, unique = true)
    @JsonProperty
    private UUID id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "wing_id", referencedColumnName = "id")
    private Wing wing;

    @Column(name = "name")
    @JsonProperty
    private String name;

    @Column(name = "type")
    @JsonProperty
    private String type;

    @Column(name = "capacity")
    @JsonProperty
    private int capacity;

    @Column(name = "multireservable")
    @JsonProperty
    private boolean multireservable;

    @Column(name = "created_at")
    @JsonProperty
    private LocalDateTime createdAt;
}
