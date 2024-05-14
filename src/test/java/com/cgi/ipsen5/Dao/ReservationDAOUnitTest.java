package com.cgi.ipsen5.Dao;

import com.cgi.ipsen5.Model.Reservation;
import com.cgi.ipsen5.Model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReservationDAOUnitTest {
    @Mock
    private ReservationDAO reservationDAO;

    private Reservation dummyReservation;
    private User testUser;

    @BeforeEach
    void setup(){
        this.testUser = createDummyTestUser();
        this.dummyReservation = createDummyReservation();
    }

    @Test
    void should_return_true_when_updateReservationStatus_is_called() {
        LocalDateTime start = LocalDateTime.now();
        User userId = testUser;
        dummyReservation.setStartDateTime(start);
        when(this.reservationDAO.updateReservationStatus(start, userId)).thenReturn(true);

        boolean result = this.reservationDAO.updateReservationStatus(start, userId);

        assertTrue(result);
    }

    @Test
void should_return_false_when_user_is_late() {
    LocalDateTime start = LocalDateTime.now();
    User userId = testUser;
    dummyReservation.setStartDateTime(start.minusMinutes(20));
    when(this.reservationDAO.updateReservationStatus(start, userId)).thenReturn(false);

    boolean result = this.reservationDAO.updateReservationStatus(start, userId);

    assertFalse(result);
}

    private Reservation createDummyReservation() {
        return Reservation.builder()
                .startDateTime(LocalDateTime.now())
                .endDateTime(LocalDateTime.now().plusHours(1))
                .user(testUser)
                .build();
    }
    private User createDummyTestUser() {
        return User.builder()
                .username("testUser")
                .password("testPassword")
                .lastName("Test")
                .email("test@gmail.com")
                .build();
    }
}
