package com.cgi.ipsen5.Service;

import com.cgi.ipsen5.Dao.LocationDao;
import com.cgi.ipsen5.Dao.ReservationDao;
import com.cgi.ipsen5.Dto.Reservation.Location.LocationCreateEditDTO;
import com.cgi.ipsen5.Exception.LocationNotFoundException;
import com.cgi.ipsen5.Model.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class LocationService {
    private final LocationDao locationDao;
    private final ReservationDao reservationDao;
    private final BuildingService buildingService;
    private final WingService wingService;

    public LocationService(LocationDao locationDao, ReservationDao reservationDao, BuildingService buildingService, WingService wingService) {
        this.locationDao = locationDao;
        this.reservationDao = reservationDao;
        this.buildingService = buildingService;
        this.wingService = wingService;
    }

    public List<Location> findAvailableLocationsByWingId(UUID wingId, LocalDateTime start, LocalDateTime end) {
        //TODO: Ik heb nu geen manier om de beschikbaarheid van een locatie te checken, dit moet nog misschien verbterd worden
        List<Location> allLocations = this.locationDao.findAllByWingId(wingId);
        List<Location> availableLocations = new ArrayList<>();

        for (Location location : allLocations) {
            List<Reservation> reservations = this.reservationDao.findReservationsBetween(location, start, end);
            if (reservations.isEmpty()) {
                availableLocations.add(location);
            }
        }

        return availableLocations;
    }

    public Location getRandomAvailableLocationIndex(List<Location> availableLocations) {
        Random rand = new Random();
        int randomIndex = rand.nextInt(availableLocations.size());
        return availableLocations.get(randomIndex);
    }

    public List<Location> getLocationsByBuildingId(String buildingName) {
        Building building = this.buildingService.getBuildingByName(buildingName);
        return this.locationDao.findAllByBuildingId(building.getId());
    }

    public Location createNewLocation(LocationCreateEditDTO locationCreateDTO) {
        Wing wing = this.wingService.getWingById(locationCreateDTO.getWing().getId());
        boolean isMultireservable = false;

        if(locationCreateDTO.getType() == LocationType.FLEXPLEK){
            isMultireservable = true;
        }

        Location location = Location.builder()
                .wing(wing)
                .name(locationCreateDTO.getName())
                .type(locationCreateDTO.getType())
                .capacity(locationCreateDTO.getCapacity())
                .multireservable(isMultireservable)
                .createdAt(LocalDateTime.now())
                .build();

        return this.locationDao.save(location);
    }

    public Location editLocation(UUID locationId, LocationCreateEditDTO locationEditDTO) {
        Location location = this.getLocationById(locationId);
        Wing wing = this.wingService.getWingById(locationEditDTO.getWing().getId());
        boolean isMultireservable = false;

        if(locationEditDTO.getType() == LocationType.FLEXPLEK){
            isMultireservable = true;
        }

        location.setName(locationEditDTO.getName());
        location.setType(locationEditDTO.getType());
        location.setMultireservable(isMultireservable);
        location.setWing(wing);
        location.setCapacity(locationEditDTO.getCapacity());

        return this.locationDao.save(location);
    }

    public Location getLocationById(UUID locationId) {
        Location location = this.locationDao.getLocationById(locationId);

        if(location == null) {
            throw new LocationNotFoundException("Location not found");
        }

        return location;
    }
}
