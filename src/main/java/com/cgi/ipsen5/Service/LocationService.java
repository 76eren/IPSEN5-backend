package com.cgi.ipsen5.Service;

import com.cgi.ipsen5.Dao.LocationDao;
import com.cgi.ipsen5.Dao.ReservationDao;
import com.cgi.ipsen5.Dto.Reservation.Location.LocationCreateEditDTO;
import com.cgi.ipsen5.Exception.LocationNotFoundException;
import com.cgi.ipsen5.Model.*;
import com.cgi.ipsen5.Exception.LocationNotFoundException;
import com.cgi.ipsen5.Model.Location;
import com.cgi.ipsen5.Model.LocationType;
import com.cgi.ipsen5.Model.Reservation;
import org.jetbrains.annotations.NotNull;
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

    public Location getLocationById(UUID locationId) {
        return this.locationDao.getLocationById(locationId).orElseThrow(
                () -> new LocationNotFoundException("Location not found"));
    }

    public List<Location> findAvailableLocationsByWingId(UUID wingId, LocalDateTime start, LocalDateTime end) {
        List<Location> allLocations = this.locationDao.findAllByWingId(wingId);
        return getLocations(start, end, allLocations);
    }

    public List<Location> findAvailableRooms(UUID buildingId, Integer numberOfPeople, LocalDateTime start, LocalDateTime end) {
        List<Location> allLocations = this.locationDao.findAllByCapacity(buildingId, numberOfPeople);
        if (allLocations.isEmpty()) {
            allLocations = this.locationDao.findAllByWingFloorBuildingId(buildingId);
        }
        return getLocations(start, end, allLocations);
    }

    @NotNull
    private List<Location> getLocations(LocalDateTime start, LocalDateTime end, @NotNull List<Location> allLocations) {
        List<Location> availableLocations = new ArrayList<>();

        for (Location location : allLocations) {
            List<Reservation> overlappingReservations = this.reservationDao.findReservationsBetween(location, start, end);
            if (overlappingReservations.isEmpty()) {
                availableLocations.add(location);
            }
        }

        return availableLocations;
    }

    public void isRoomAvailable(UUID locationId, LocalDateTime start, LocalDateTime end) {
        Location location = this.getLocationById(locationId);
        List<Reservation> reservations = this.reservationDao.findReservationsBetween(location, start, end);
        if (!reservations.isEmpty()) {
            throw new LocationNotFoundException("Location is not available at chosen date and time");
        }
    }

    public void existsById(UUID locationId) {
        if (!this.locationDao.existsById(locationId)) {
            throw new LocationNotFoundException("Location not found");
        }
    }

    public void isLocationRoom(UUID locationId) {
        this.existsById(locationId);
        if (this.getLocationById(locationId).getType() != LocationType.ROOM) {
            throw new LocationNotFoundException("Location is not a room");
        }
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

        if(locationCreateDTO.getType() == LocationType.WORKPLACE){
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

        if(locationEditDTO.getType() == LocationType.WORKPLACE){
            isMultireservable = true;
        }

        location.setName(locationEditDTO.getName());
        location.setType(locationEditDTO.getType());
        location.setMultireservable(isMultireservable);
        location.setWing(wing);
        location.setCapacity(locationEditDTO.getCapacity());

        return this.locationDao.save(location);
    }

    public void remove(UUID id) {
        if(!this.locationDao.existsById(id)) {
            throw new LocationNotFoundException("Location not found");
        }

        this.locationDao.remove(id);
    }
}
