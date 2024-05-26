package com.cgi.ipsen5.Seeder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder {
    private final BuildingSeeder buildingSeeder;
    private final UserSeeder userSeeder;
    private final FloorSeeder floorSeeder;
    private final WingSeeder wingSeeder;
    private final LocationSeeder locationSeeder;
    private final ReservationSeeder reservationSeeder;
    private final ReservationHistorySeeder reservationHistorySeeder;

    private boolean alreadySeeded = false;

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        if (alreadySeeded) {
            return;
        }

        buildingSeeder.seed();
        userSeeder.seed();
        floorSeeder.seed();
        wingSeeder.seed();
        locationSeeder.seed();

        reservationSeeder.seed();
        reservationHistorySeeder.seed();

        this.alreadySeeded = true;
    }
}
