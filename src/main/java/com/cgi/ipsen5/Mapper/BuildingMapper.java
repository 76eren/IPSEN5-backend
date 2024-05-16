package com.cgi.ipsen5.Mapper;

import com.cgi.ipsen5.Dto.Reserve.Building.BuildingDTO;
import com.cgi.ipsen5.Model.Building;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BuildingMapper {

    public BuildingDTO fromEntity(Building building) {
        return BuildingDTO.builder()
                .name(building.getName())
                .address(building.getAddress())
                .build();
    }
}
