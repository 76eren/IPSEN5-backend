package com.cgi.ipsen5.Seeder;


import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder {
    private final BuildingSeeder buildingSeeder;

    private boolean hasSeeded = false;

    @EventListener
    public void seed(ContextRefreshedEvent ignored) {
        if (hasSeeded) {
            return;
        }

        buildingSeeder.seed();

        this.hasSeeded = true;
    }
}
