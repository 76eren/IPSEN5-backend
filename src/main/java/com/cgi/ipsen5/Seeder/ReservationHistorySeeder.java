package com.cgi.ipsen5.Seeder;

import com.cgi.ipsen5.Dao.LocationDao;
import com.cgi.ipsen5.Dao.UserDao;
import com.cgi.ipsen5.Model.*;
import com.cgi.ipsen5.Repository.ReservationHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class ReservationHistorySeeder {
    @Autowired
    private ReservationHistoryRepository reservationHistoryRepository;

    @Autowired
    private LocationDao locationDao;

    @Autowired
    private UserDao userDao;

    public void seed() {
        for(ReservationHistory reservationHistory : getData()){
            this.reservationHistoryRepository.save(reservationHistory);
        }

    }

    public List<ReservationHistory> getData() {
        ArrayList<ReservationHistory> reservationHistories = new ArrayList<>();
        List<Location> locations = this.locationDao.getAll();
        User user = this.userDao.findAllUsers().get(0);
        Location singleLocation = locations.get(0);
        reservationHistories.add(this.createReservationHistory(singleLocation, user, 2));
        reservationHistories.add(this.createReservationHistory(singleLocation, user, 2));
        reservationHistories.add(this.createReservationHistory(singleLocation, user, 2));
        reservationHistories.add(this.createReservationHistory(singleLocation, user, 1));
        reservationHistories.add(this.createReservationHistory(singleLocation, user, 1));
        reservationHistories.add(this.createReservationHistory(singleLocation, user, 0));

        for(Location location : locations){
            int randomNumber = (int) (Math.random() * 3);
            reservationHistories.add(this.createReservationHistory(location, user, randomNumber));
        }

        return reservationHistories;
    }

    public ReservationHistory createReservationHistory(Location location, User user, int randomNumber) {
        return ReservationHistory
                .builder()
                .user(user)
                .location(location)
                .status(ReservationStatus.CHECKED_IN)
                .startDateTime(LocalDateTime.now().minusYears(randomNumber))
                .endDateTime(LocalDateTime.now().minusYears(randomNumber).plusHours(2))
                .numberOfPeople(5)
                .build();
    }
}
