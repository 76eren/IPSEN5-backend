package com.cgi.ipsen5.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "wing")
@Getter
@Setter
public class Wing {
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
    @JoinColumn(name = "floor_id", referencedColumnName = "id")
    private Floor floor;

    @Column(name = "name")
    @JsonProperty
    private String name;
}
