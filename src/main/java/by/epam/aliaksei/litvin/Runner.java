package by.epam.aliaksei.litvin;

import by.epam.aliaksei.litvin.config.AppConfig;
import by.epam.aliaksei.litvin.domain.*;
import by.epam.aliaksei.litvin.service.BookingService;
import by.epam.aliaksei.litvin.service.UserService;
import by.epam.aliaksei.litvin.service.impl.BookingServiceImpl;
import by.epam.aliaksei.litvin.service.impl.UserServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDateTime;
import java.util.*;

public class Runner {

    public static void main(String[] args) {

        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        UserService userService = ctx.getBean(UserServiceImpl.class);
        BookingService bookingService = ctx.getBean(BookingServiceImpl.class);

        Auditorium auditorium = new Auditorium();
        auditorium.setName("Big");
        auditorium.setNumberOfSeats(200);
        auditorium.setVipSeats(new HashSet<Long>(){{
            add((long) 5);
            add((long) 10);
            add((long) 15);
        }});

        Event event = new Event();
        event.setName("Movie#1");
        event.setBasePrice(100);
        event.setRating(EventRating.HIGH);
        event.setAuditoriums(new TreeMap<LocalDateTime, Auditorium>(){{
            put(LocalDateTime.now(), auditorium);
        }});

        User user = new User();
        user.setFirstName("Alexey");
        user.setLastName("Litvin");


        userService.save(user);

        Ticket ticket1 = new Ticket(user, event, LocalDateTime.now(), 10);
        Ticket ticket2 = new Ticket(user, event, LocalDateTime.now(), 11);
        NavigableSet<Ticket> tickets = new TreeSet<>();
        tickets.add(ticket1);
        tickets.add(ticket2);

        user.setTickets(tickets);

        bookingService.bookTickets(tickets);

    }
}
