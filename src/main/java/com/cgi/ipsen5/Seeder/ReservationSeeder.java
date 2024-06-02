package com.cgi.ipsen5.Seeder;

import com.cgi.ipsen5.Dao.LocationDao;
import com.cgi.ipsen5.Dao.ReservationDao;
import com.cgi.ipsen5.Dao.UserDao;
import com.cgi.ipsen5.Model.Location;
import com.cgi.ipsen5.Model.Reservation;
import com.cgi.ipsen5.Model.Role;
import com.cgi.ipsen5.Model.User;
import com.cgi.ipsen5.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ReservationSeeder {
    private final ReservationDao reservationDAO;
    private final UserDao userDao;
    private final LocationDao locationDao;

    @Transactional
    public void seed() {
        User user = this.userDao.findAllUsers().getFirst();

        createReservation(user, LocalDateTime.now().plusDays((long) (5 + (Math.random() * (10 - 5)))), LocalDateTime.now().plusDays((long) (5 + (Math.random() * (10 - 5)))).plusHours(2), 2);
        createReservation(user, LocalDateTime.now().plusDays((long) (5 + (Math.random() * (10 - 5)))), LocalDateTime.now().plusDays((long) (5 + (Math.random() * (10 - 5)))).plusHours(2), 2);
        createReservation(user, LocalDateTime.now().plusDays((long) (5 + (Math.random() * (10 - 5)))), LocalDateTime.now().plusDays((long) (5 + (Math.random() * (10 - 5)))).plusHours(2), 2);
        createReservation(user, LocalDateTime.now().plusDays((long) (5 + (Math.random() * (10 - 5)))), LocalDateTime.now().plusDays((long) (5 + (Math.random() * (10 - 5)))).plusHours(2), 2);
        createReservation(user, LocalDateTime.now().plusDays((long) (5 + (Math.random() * (10 - 5)))), LocalDateTime.now().plusDays((long) (5 + (Math.random() * (10 - 5)))).plusHours(2), 2);
    }

    public void createReservation(User user, LocalDateTime startDateTime, LocalDateTime endDateTime, int numberOfPeople) {
        Location location = locationDao.getAll().getFirst();

        Reservation reservation = Reservation.builder()
                .startDateTime(startDateTime)
                .endDateTime(endDateTime)
                .user(user)
                .createdAt(LocalDateTime.now())
                .numberOfPeople(numberOfPeople)
                .location(location)
                .build();

        reservationDAO.save(reservation);
    }
}
