package by.epam.aliaksei.litvin.aspects;

import by.epam.aliaksei.litvin.config.TestAppConfig;
import by.epam.aliaksei.litvin.domain.*;
import by.epam.aliaksei.litvin.service.BookingService;
import by.epam.aliaksei.litvin.service.EventService;
import by.epam.aliaksei.litvin.service.UserService;
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
public class CounterAspectTest {

    private static final String EVENT_NAME = "Movie#1";

    @Autowired
    private BookingService bookingService;

    @Autowired
    private EventService eventService;

    @Autowired
    private CounterAspect counterAspect;

    @Autowired
    private UserService userService;

    private Auditorium auditorium;
    private Event event;
    private Set<Long> seats;
    private User user;
    private NavigableSet<Ticket> tickets1;
    private NavigableSet<Ticket> tickets2;

    @Before
    public void setUp() {

        user = new User();
        user.setFirstName("Alexey");
        user.setLastName("Litvin");

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
            put(LocalDateTime.now(), auditorium);
        }});

        Ticket ticket1 = new Ticket(user, event, LocalDateTime.now(), 10);
        tickets1 = new TreeSet<>();
        tickets1.add(ticket1);

        Ticket ticket2 = new Ticket(user, event, LocalDateTime.now(), 11);
        tickets2 = new TreeSet<>();
        tickets2.add(ticket2);

        user.setTickets(tickets1);

        seats = new HashSet<>();
        seats.add((long) 1);
        seats.add((long) 2);
        seats.add((long) 3);
        seats.add((long) 4);
        seats.add((long) 5);

        userService.save(user);
        eventService.save(event);
    }

    @After
    public void tearDown() {
        eventService.removeAll();
        userService.removeAll();
    }

    @Test
    public void test_accessByName() {
        Event event = eventService.getByName(EVENT_NAME);
        eventService.getByName(EVENT_NAME);

        Integer accessesNumber = counterAspect.getEventsAccessedByName().get(event);

        assertEquals(accessesNumber.longValue(), 2);
    }

    @Test
    public void test_priceQueriedCounter() {
        bookingService.getTicketsPrice(event, LocalDateTime.now(), new User(), seats);
        bookingService.getTicketsPrice(event, LocalDateTime.now(), new User(), seats);

        Map<Event, Integer> priceQueriedNumbers = counterAspect.getPriceQueriedNumbers();
        Integer accessesNumber = priceQueriedNumbers.get(event);

        assertEquals(accessesNumber.longValue(), 2);
    }

    @Test
    public void test_bookTicket() {
        bookingService.bookTickets(tickets1);
        bookingService.bookTickets(tickets2);
        int callsNumber = counterAspect.getTicketsBookedCounter().get(event);
        assertEquals(callsNumber, 2);
    }

}
