package com.cgi.ipsen5.Service;

import com.cgi.ipsen5.Dao.LocationDao;
import com.cgi.ipsen5.Dao.ReservationDao;
import com.cgi.ipsen5.Model.Location;
import com.cgi.ipsen5.Model.Reservation;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class LocationService {
    private final LocationDao locationDao;
    private final ReservationDao reservationDao;

    public LocationService(LocationDao locationDao, ReservationDao reservationDao) {
        this.locationDao = locationDao;
        this.reservationDao = reservationDao;
    }

    public List<Location> findAvailableLocationsByWingId(UUID wingId, LocalDateTime start, LocalDateTime end) {
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
}
