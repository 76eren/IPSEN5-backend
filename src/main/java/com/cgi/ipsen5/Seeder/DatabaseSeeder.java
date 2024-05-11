package com.cgi.ipsen5.Seeder;


import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder {
    private final BuildingSeeder buildingSeeder;
    private final UserSeeder userSeeder;
    private final FloorSeeder floorSeeder;

    private boolean hasSeeded = false;

    @EventListener
    public void seed(ContextRefreshedEvent ignored) {
        if (hasSeeded) {
            return;
        }

        this.buildingSeeder.seed();
        this.userSeeder.seed();
        this.floorSeeder.seed();

        this.hasSeeded = true;
    }
}
