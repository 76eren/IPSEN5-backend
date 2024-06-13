package com.cgi.ipsen5.Dto.Reservation.Location;

import com.cgi.ipsen5.Model.LocationType;
import com.cgi.ipsen5.Model.Wing;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationCreateEditDTO {
    @NotNull(message = "Wing is required")
    private Wing wing;
    @NotBlank(message = "Name is required")
    private String name;
    @NotNull(message = "Location type is required")
    private LocationType type;
    @NotNull(message = "Capacity is required")
    @Positive(message = "Capacity should be greater than 0")
    private int capacity;
}
