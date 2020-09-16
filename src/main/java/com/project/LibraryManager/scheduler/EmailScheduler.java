package com.project.LibraryManager.scheduler;

import com.project.LibraryManager.domain.Rental;
import com.project.LibraryManager.repository.RentalReposiotry;
import com.project.LibraryManager.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailScheduler {

    @Autowired
    private EmailService emailService;

    @Scheduled(cron = "0 0 8 * * MON")
    public void sendWeeklyRentalsNotifications(){
        emailService.sendWeeklyNotificationToActiveRentals();
    }

}
