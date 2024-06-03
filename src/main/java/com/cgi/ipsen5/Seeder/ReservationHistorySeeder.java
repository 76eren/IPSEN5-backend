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

    private ArrayList<ReservationHistory> reservationHistories;
    private List<Location> locations;
    private List<User> users;


    public void seed() {
        this.reservationHistories = new ArrayList<>();
        this.locations = this.locationDao.getAll();
        this.users = this.userDao.findAllUsers();

        this.createRoomOccupancyData();
        this.createNoShowData();
    }

    public void createRoomOccupancyData() {
        for(Location location : locations) {
            for(User user : users) {
                int randomNumber = (int) (Math.random() * 3);
                reservationHistories.add(this.createReservationHistory(location, user, randomNumber, ReservationStatus.CHECKED_IN));
            }
        }

        this.reservationHistoryRepository.saveAll(reservationHistories);
    }

    public void createNoShowData() {
        for(User user : users) {
            int randomNumber = (int) (Math.random() * 5);
            for(int i = 0; i < randomNumber; i++){
                reservationHistories.add(this.createReservationHistory(locations.get(0), user, 0, ReservationStatus.NOT_CHECKED_IN));
            }
        }

        this.reservationHistoryRepository.saveAll(reservationHistories);
    }


    public ReservationHistory createReservationHistory(Location location, User user, int randomNumber, ReservationStatus status) {
        return ReservationHistory
                .builder()
                .user(user)
                .location(location)
                .status(status)
                .startDateTime(LocalDateTime.now().minusYears(randomNumber))
                .endDateTime(LocalDateTime.now().minusYears(randomNumber).plusHours(2))
                .numberOfPeople(5)
                .build();
    }
}
