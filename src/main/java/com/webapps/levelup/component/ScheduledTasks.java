package com.webapps.levelup.component;

import com.webapps.levelup.service.apartment.ApartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScheduledTasks {
    private final ApartmentService apartmentService;
    //Cron expression for every day at 00:00
    public static final String CRON_EXPRESSION = "0 0 0 * * ?";

    @Scheduled(cron = CRON_EXPRESSION)
    public void uploadXmlFile() {
        apartmentService.uploadXmlFile();
    }
}
