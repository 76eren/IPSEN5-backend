package com.cgi.ipsen5.Config;

import com.cgi.ipsen5.Service.ExpiredResetTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ScheduledTokenRemoval {
    private final ExpiredResetTokenService expiredResetTokenService;

    @Scheduled(cron = "@daily", zone = "Europe/Amsterdam")
    public void removeExpiredPasswordTokens(){
        this.expiredResetTokenService.removeExpiredTokens();
    }

}
