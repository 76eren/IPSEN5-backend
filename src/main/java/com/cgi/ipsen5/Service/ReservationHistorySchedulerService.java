package com.cgi.ipsen5.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class ReservationHistorySchedulerService {

    private final ReservationService reservationService;

    @Scheduled(cron = "* * * * * *")
    public void updateReservationHistory() {
        LocalDateTime now = LocalDateTime.now();
        reservationService.moveOldReservationsToHistory(now);
    }
}
