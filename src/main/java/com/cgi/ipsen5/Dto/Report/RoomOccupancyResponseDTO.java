package com.cgi.ipsen5.Dto.Report;

import java.time.LocalDateTime;

public interface RoomOccupancyResponseDTO {
    String getRoom();
    Long getNumberOfUsages();
    LocalDateTime getDate();
}