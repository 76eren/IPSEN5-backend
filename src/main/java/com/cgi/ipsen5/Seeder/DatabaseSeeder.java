package com.cgi.ipsen5.Seeder;


import com.cgi.ipsen5.Model.Building;
import com.cgi.ipsen5.Model.Floor;
import com.cgi.ipsen5.Model.User;
import com.cgi.ipsen5.Model.Wing;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder {
    private final BuildingSeeder buildingSeeder;
    private final UserSeeder userSeeder;
    private final FloorSeeder floorSeeder;
    private final WingSeeder wingSeeder;

    private final LocationRequestData locationRequestData;

    private boolean hasSeeded = false;

    @EventListener
    public void seed(ContextRefreshedEvent ignored) {
        if (hasSeeded) {
            return;
        }

        this.buildingSeeder.seed();
        this.userSeeder.seed();
        this.floorSeeder.seed();
        this.wingSeeder.seed();

        this.hasSeeded = true;

        this.locationRequestData.collectRandomLocationDataToMakeATestRequest();
    }
}
