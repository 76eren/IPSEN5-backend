package com.cgi.ipsen5.Seeder;

import com.cgi.ipsen5.Dao.ReservationDAO;
import com.cgi.ipsen5.Model.Reservation;
import com.cgi.ipsen5.Model.Role;
import com.cgi.ipsen5.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ReservationSeeder {
    @Autowired
    private ReservationDAO reservationDAO;

    public void seed() {
        User testUser = User.builder()
                .username("testUser")
                .password("testPassword")
                .lastName("Test")
                .username("test@example.com")
                .phoneNumber("1234567890")
                .role(Role.USER)
                .build();
        Reservation reservation = Reservation.builder()
                .startDateTime(LocalDateTime.now())
                .endDateTime(LocalDateTime.now().plusHours(1))
                .user(testUser)
                .build();
        reservationDAO.createReservation(reservation);
    }
}
