package com.cgi.ipsen5.Dto.Location;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AvailableRoomsDTO {
    @NotBlank(message = "building id is required")
    private UUID buildingId;
    @NotNull(message = "Number of attendees is required")
    @Min(value = 1, message = "Number of attendees should be greater than 0")
    private Integer numberOfPeople;
    @NotBlank(message = "Start date time is required")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}$", message = "Invalid date time")
    private String startDateTime; // Should be in the format like "2024-05-11T14:30:00"
    @NotBlank(message = "End date time is required")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}$", message = "Invalid date time")
    private String endDateTime; // Should be in the format like "2024-05-11T14:30:00"
}
