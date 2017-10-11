package by.epam.aliaksei.litvin.services;

import by.epam.aliaksei.litvin.config.TestAppConfig;
import by.epam.aliaksei.litvin.domain.*;
import by.epam.aliaksei.litvin.service.impl.BookingServiceImpl;
import by.epam.aliaksei.litvin.service.impl.UserServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestAppConfig.class, loader = AnnotationConfigContextLoader.class)
public class BookingServiceImplTest {

    @Autowired
    private BookingServiceImpl bookingService;

    @Autowired
    private UserServiceImpl userService;

    private Event event;
    private Auditorium auditorium;
    private User user;
    private LocalDateTime localDateTime = LocalDateTime.now().plusDays(1);
    private Ticket ticket;

    @Before
    public void setUp() {
        auditorium = new Auditorium();
        auditorium.setName("Big");
        auditorium.setNumberOfSeats(200);
        auditorium.setVipSeats(new HashSet<Long>(){{
            add((long) 5);
            add((long) 10);
            add((long) 15);
        }});

        event = new Event();
        event.setName("Movie#1");
        event.setBasePrice(100);
        event.setRating(EventRating.HIGH);
        event.setAuditoriums(new TreeMap<LocalDateTime, Auditorium>(){{
            put(localDateTime, auditorium);
        }});

        ticket = new Ticket(user, event, localDateTime, 10);
        NavigableSet<Ticket> tickets = new TreeSet<>();
        tickets.add(ticket);

        user = new User();
        user.setFirstName("Alexey");
        user.setLastName("Litvin");
        user.setTickets(tickets);
        userService.save(user);
    }

    @After
    public void tearDown() {
        userService.removeAll();
    }

    @Test
    public void test_getTicketsPrice() {
        double expectedResult = 100 * 1.2 * 4 + 100 * 1.2 * 3;
        Set<Long> seats = new HashSet<>();
        seats.add((long) 1);
        seats.add((long) 2);
        seats.add((long) 3);
        seats.add((long) 4);
        seats.add((long) 5);
        double ticketsPrice = bookingService.getTicketsPrice(event, localDateTime, new User(), seats);
        assertEquals(ticketsPrice, expectedResult, 0);
    }

    @Test
    public void test_getPurchasedTicketsForEvent() {
        Set<Ticket> purchasedTicketsForEvent = bookingService.getPurchasedTicketsForEvent(event, localDateTime);
        assertEquals(purchasedTicketsForEvent.size(), 1);
    }

}
