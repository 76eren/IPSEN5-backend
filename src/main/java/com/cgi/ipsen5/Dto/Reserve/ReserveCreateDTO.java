package com.cgi.ipsen5.Dto.Reserve;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReserveCreateDTO {
    private String name;

    private String wingId;
    private String floorId;
    private String buildingId;

    private ReserveType type;
    private int capacity;
    private String created_at; // Needs to be yyyy-MM-dd'T'HH:mm:ss format from the front-end
    private boolean multireservable;
}


