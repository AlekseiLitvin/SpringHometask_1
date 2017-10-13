package by.epam.aliaksei.litvin;

import by.epam.aliaksei.litvin.aspects.CounterAspect;
import by.epam.aliaksei.litvin.config.AppConfig;
import by.epam.aliaksei.litvin.domain.Auditorium;
import by.epam.aliaksei.litvin.domain.Event;
import by.epam.aliaksei.litvin.domain.EventRating;
import by.epam.aliaksei.litvin.domain.User;
import by.epam.aliaksei.litvin.service.EventService;
import by.epam.aliaksei.litvin.service.impl.BookingServiceImpl;
import by.epam.aliaksei.litvin.service.impl.EventServiceImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import java.time.LocalDateTime;
import java.util.*;

public class Runner {

    public static void main(String[] args) {

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


        Set<Long> seats = new HashSet<>();
        seats.add((long) 1);
        seats.add((long) 2);
        seats.add((long) 3);
        seats.add((long) 4);
        seats.add((long) 5);

        AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        EventService eventServiceImpl = ctx.getBean(EventServiceImpl.class);
        BookingServiceImpl bookingServiceImpl = ctx.getBean(BookingServiceImpl.class);
        eventServiceImpl.save(event);

        bookingServiceImpl.getTicketsPrice(event, LocalDateTime.now(), new User(), seats);


        CounterAspect bean1 = ctx.getBean(CounterAspect.class);
        System.out.println(bean1.getPriceQueriedNumbers());
    }
}
