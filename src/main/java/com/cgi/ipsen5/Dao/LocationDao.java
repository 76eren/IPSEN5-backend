package com.cgi.ipsen5.Dao;


import com.cgi.ipsen5.Dto.Reserve.ReserveCreateDTO;
import com.cgi.ipsen5.Model.*;
import com.cgi.ipsen5.Repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class LocationDao {
    private final LocationRepository locationRepository;
    private final BuildingDao buildingDao;
    private final FloorDao floorDao;
    private final WingDao wingDao;

    public Location create(ReserveCreateDTO reserveCreateDTO) {
        Building building = buildingDao.getBuildingById(reserveCreateDTO.getBuildingId());
        if (building == null) {
            return null;
        }

        Floor floor = this.floorDao
                .findAll()
                .stream()
                .filter(f -> f.getBuilding()
                        .getId()
                        .equals(building.getId()))
                .findFirst()
                .get();

        Wing wing = this.wingDao
                .findAll()
                .stream()
                .filter(w -> w.getFloor()
                        .getId()
                        .equals(floor.getId()))
                .findFirst()
                .get();


        return Location
                .builder()
                .id(UUID.randomUUID())
                .name(reserveCreateDTO.getName())
                .type(reserveCreateDTO.getType().toString())
                .createdAt(stringToLocalDate(reserveCreateDTO.getCreated_at()))
                .capacity(reserveCreateDTO.getCapacity())
                .multireservable(reserveCreateDTO.isMultireservable())
                .wing(wing)
                .build();
    }

    private LocalDateTime stringToLocalDate(String date) {
        // convert a string (for example '2024-05-11T14:30:00') to a LocalDateTime object
        return LocalDateTime.parse(date);
    }

    private String localDateToString(LocalDateTime date) {
        return date.toString();
    }


}
