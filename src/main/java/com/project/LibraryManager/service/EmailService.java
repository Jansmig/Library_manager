package com.project.LibraryManager.service;

import com.project.LibraryManager.domain.Mail;
import com.project.LibraryManager.domain.Rental;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private RentalService rentalService;


    private SimpleMailMessage createMailMessage(Mail mail) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(mail.getTo());
        simpleMailMessage.setSubject(mail.getSubject());
        simpleMailMessage.setText(mail.getMessage());

        return simpleMailMessage;
    }

    public void sendSimpleEmail(final Mail mail) {
        LOGGER.info("Preparing to send email.");
        try {
            javaMailSender.send(createMailMessage(mail));
            LOGGER.info("Email has been sent to: " + mail.getTo());
        } catch (MailException e) {
            LOGGER.error("Email failed. ", e.getMessage(), e);
        }
    }


    public void sendWeeklyNotificationToActiveRentals() {
        LOGGER.info("Preparing to send weekly notification emails to all active book rentals.");

        List<Rental> rentals = new ArrayList<>();
        rentals = rentalService.getActiveRentals();

        for (Rental rental : rentals) {
            try {
                String message = "Dear " + rental.getUser().getFirstName() + ", please be informed that book: \"" +
                        rental.getBook().getOrigin().getTitle() + "\" is still rented by you." +
                        " When you are done with reading, please return the book to our library so other members can enjoy it as well!";

                SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
                simpleMailMessage.setTo(rental.getUser().getEmail());
                simpleMailMessage.setSubject("Weekly notification");
                simpleMailMessage.setText(message);

                javaMailSender.send(simpleMailMessage);
                LOGGER.info("Email has been sent to: " + rental.getUser().getEmail());
            } catch (MailException e) {
                LOGGER.error("Error has occured. ", e.getMessage(), e);
            }
        }
    }

}
