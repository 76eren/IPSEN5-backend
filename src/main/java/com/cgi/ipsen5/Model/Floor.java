package com.cgi.ipsen5.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "floor")
public class Floor {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(nullable = false, unique = true)
    @JsonIgnore
    private UUID id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "building_id", referencedColumnName = "id")
    private Building building;

    @Column(name = "number")
    @JsonProperty
    private String number;
}
