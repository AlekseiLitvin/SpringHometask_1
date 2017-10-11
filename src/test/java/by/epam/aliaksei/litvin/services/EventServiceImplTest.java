package by.epam.aliaksei.litvin.services;

import by.epam.aliaksei.litvin.config.TestAppConfig;
import by.epam.aliaksei.litvin.domain.Event;
import by.epam.aliaksei.litvin.service.impl.EventServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestAppConfig.class, loader = AnnotationConfigContextLoader.class)
public class EventServiceImplTest {

    private static final String EVENT_NAME = "name";


    @Autowired
    private EventServiceImpl eventService;

    @Before
    public void setup() {
        Event event1 = new Event();
        Event event2 = new Event();
        Event event3 = new Event();

        event1.setName(EVENT_NAME);
        NavigableSet<LocalDateTime> airDates1 = new TreeSet<>();
        airDates1.add(LocalDateTime.now().plusHours(1));
        airDates1.add(LocalDateTime.now().plusDays(1));
        airDates1.add(LocalDateTime.now().plusDays(7));
        event1.setAirDates(airDates1);

        NavigableSet<LocalDateTime> airDates2 = new TreeSet<>();
        airDates2.add(LocalDateTime.now().plusDays(1));
        event2.setAirDates(airDates2);

        eventService.save(event1);
        eventService.save(event2);
        eventService.save(event3);
    }

    @After
    public void tearDown() {
        eventService.removeAll();
    }

    @Test
    public void test_getForDateRange() {
        Set<Event> events = eventService.getForDateRange(LocalDate.now().minusDays(1), LocalDate.now().plusDays(2));
        assertEquals(events.size(), 2);
    }

    @Test
    public void test_getByName() {
        Event eventByName = eventService.getByName(EVENT_NAME);
        assertEquals(eventByName.getName(), EVENT_NAME);
    }

    @Test
    public void test_getNextEvents() {
        Set<Event> nextEvents = eventService.getNextEvents(LocalDateTime.now().plusDays(2));
        assertEquals(nextEvents.size(), 3);
    }


}
