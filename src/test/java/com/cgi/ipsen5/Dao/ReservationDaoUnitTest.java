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
public class ReservationDaoUnitTest {
    @Mock
    private ReservationDao reservationDAO;
    @Mock
    private ReservationHistoryDao reservationHistoryDAO;

    private Reservation dummyReservation;
    private User testUser;
    LocalDateTime start = LocalDateTime.now();

    @BeforeEach
    void setup(){
        this.testUser = createDummyTestUser();
        this.dummyReservation = createDummyReservation();
    }

    @Test
    void should_return_true_when_updateReservationStatus_is_called() {
        User userId = testUser;
        dummyReservation.setStartDateTime(start);
        when(this.reservationDAO.updateReservationStatus(start, userId)).thenReturn(true);

        boolean result = this.reservationDAO.updateReservationStatus(start, userId);

        assertTrue(result);
    }

    @Test
    void should_return_false_when_user_is_late() {
        User userId = testUser;
        dummyReservation.setStartDateTime(start.minusMinutes(20));
        when(this.reservationDAO.updateReservationStatus(start, userId)).thenReturn(false);

        boolean result = this.reservationDAO.updateReservationStatus(start, userId);

        assertFalse(result);
    }

    @Test
    void should_return_true_and_save_reservation_in_history_when_user_cancelled_a_reservation() {
        dummyReservation.setStartDateTime(start);
        when(this.reservationDAO.cancelReservation(dummyReservation.getId())).thenReturn(true);
        when(this.reservationHistoryDAO.saveReservationHistory(dummyReservation)).thenReturn(true);

        boolean cancelResult = this.reservationDAO.cancelReservation(dummyReservation.getId());
        boolean saveResult = this.reservationHistoryDAO.saveReservationHistory(dummyReservation);

        assertTrue(cancelResult);
        assertTrue(saveResult);
    }

    @Test
    void should_return_false_and_not_save_reservation_in_history_when_user_cancelled_a_reservation() {
        dummyReservation.setStartDateTime(start);
        when(this.reservationDAO.cancelReservation(dummyReservation.getId())).thenReturn(false);
        when(this.reservationHistoryDAO.saveReservationHistory(dummyReservation)).thenReturn(false);

        boolean cancelResult = this.reservationDAO.cancelReservation(dummyReservation.getId());
        boolean saveResult = this.reservationHistoryDAO.saveReservationHistory(dummyReservation);

        assertFalse(cancelResult);
        assertFalse(saveResult);
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
                .username("test@gmail.com")
                .password("testPassword")
                .lastName("Test")
                .build();
    }
}
