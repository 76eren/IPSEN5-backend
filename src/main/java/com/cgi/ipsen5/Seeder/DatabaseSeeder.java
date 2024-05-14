package com.cgi.ipsen5.Seeder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder {
    @Autowired
    ReservationSeeder reservationSeeder;
    private boolean alreadySeeded = false;

    @EventListener
    public void seed(ContextRefreshedEvent event){
        if(alreadySeeded){
            return;
        }
        reservationSeeder.seed();
        this.alreadySeeded = true;

    }
}
