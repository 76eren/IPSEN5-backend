package com.cgi.ipsen5.Seeder;

import com.cgi.ipsen5.Dao.ReservationDao;
import com.cgi.ipsen5.Model.Reservation;
import com.cgi.ipsen5.Model.Role;
import com.cgi.ipsen5.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ReservationSeeder {
    @Autowired
    private ReservationDao reservationDAO;

    public void seed() {
        User testUser = User.builder()
                .username("test@example.com")
                .firstName("Test")
                .password("testPassword")
                .firstName("")
                .lastName("Test")
                .phoneNumber("1234567890")
                .role(Role.USER)
                .build();
        Reservation reservation = Reservation.builder()
                .startDateTime(LocalDateTime.now())
                .endDateTime(LocalDateTime.now().plusHours(1))
                .user(testUser)
                .build();
        reservationDAO.save(reservation);
    }
}
