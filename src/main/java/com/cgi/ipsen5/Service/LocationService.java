package com.cgi.ipsen5.Service;

import com.cgi.ipsen5.Dao.LocationDao;
import com.cgi.ipsen5.Dao.ReservationDao;
import com.cgi.ipsen5.Model.Building;
import com.cgi.ipsen5.Model.Location;
import com.cgi.ipsen5.Model.Reservation;
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

    public LocationService(LocationDao locationDao, ReservationDao reservationDao, BuildingService buildingService) {
        this.locationDao = locationDao;
        this.reservationDao = reservationDao;
        this.buildingService = buildingService;
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
}
