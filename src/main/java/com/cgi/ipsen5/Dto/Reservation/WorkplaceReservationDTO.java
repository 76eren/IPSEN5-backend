package com.cgi.ipsen5.Dto.Reservation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkplaceReservationDTO {
    @NotBlank(message = "Wing id is required")
    private String wingId;
    @NotBlank(message = "Start date time is required")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}$", message = "Invalid date time")
    private String startDateTime; // Should be in the format like "2024-05-11T14:30:00"
    @NotBlank(message = "End date time is required")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}$", message = "Invalid date time")
    private String endDateTime; // Should be in the format like "2024-05-11T14:30:00"
}
