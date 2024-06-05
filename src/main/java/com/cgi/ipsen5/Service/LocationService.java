package com.cgi.ipsen5.Service;

import com.cgi.ipsen5.Dao.LocationDao;
import com.cgi.ipsen5.Dao.ReservationDao;
import com.cgi.ipsen5.Exception.LocationNotFoundException;
import com.cgi.ipsen5.Model.Location;
import com.cgi.ipsen5.Model.LocationType;
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

    public LocationService(LocationDao locationDao, ReservationDao reservationDao) {
        this.locationDao = locationDao;
        this.reservationDao = reservationDao;
    }

    public Location getLocationById(UUID locationId) {
        return this.locationDao.getLocationById(locationId).orElseThrow(
                () -> new LocationNotFoundException("Location not found"));
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

    public List<Location> findAvailableRooms(UUID buildingId, Integer numberOfPeople, LocalDateTime start, LocalDateTime end) {
        List<Location> allLocations = this.locationDao.findAllByCapacity(numberOfPeople);
        if (allLocations.isEmpty()) {
            allLocations = this.locationDao.findAllByWingFloorBuildingId(buildingId);
        }
        List<Location> availableLocations = new ArrayList<>();

        for (Location location : allLocations) {
            List<Reservation> reservations = this.reservationDao.findReservationsBetween(location, start, end);
            if (reservations.isEmpty()) {
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
}
