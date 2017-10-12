package by.epam.aliaksei.litvin.aspects;


import by.epam.aliaksei.litvin.config.TestAppConfig;
import by.epam.aliaksei.litvin.domain.Event;
import by.epam.aliaksei.litvin.domain.User;
import by.epam.aliaksei.litvin.service.DiscountService;
import by.epam.aliaksei.litvin.strategies.impl.BirthdayDiscountStrategy;
import by.epam.aliaksei.litvin.strategies.impl.EveryNTicketDiscountStrategy;
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
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestAppConfig.class, loader = AnnotationConfigContextLoader.class)
public class DiscountAspectTest {


    @Autowired
    private DiscountService discountService;

    @Autowired
    private DiscountAspect discountAspect;

    private User user;
    private User user1;

    @Before
    public void setUp() {
        user = new User();
        user.setFirstName("Alexey");
        user.setLastName("Litvin");
        user.setBirthday(LocalDate.now());

        user1 = new User();
        user1.setFirstName("Test");
        user1.setLastName("Test");
        user1.setBirthday(LocalDate.now().plusDays(10));
    }


    @Test
    public void testDiscountGivenNumber() {

        discountService.getDiscount(user, new Event(), LocalDateTime.now(), 11);
        discountService.getDiscount(user, new Event(), LocalDateTime.now(), 9);

        Map<Class, Integer> callsCounter = discountAspect.getCallsCounter();

        long birthdayDiscountCallsNumber = callsCounter.entrySet().stream()
                .map(entry -> entry.getKey().equals(BirthdayDiscountStrategy.class))
                .count();

        long everyNTicketDiscountCallsNumber = callsCounter.entrySet().stream()
                .map(entry -> entry.getKey().equals(EveryNTicketDiscountStrategy.class))
                .count();

        assertEquals(birthdayDiscountCallsNumber, 2);
        assertEquals(everyNTicketDiscountCallsNumber, 2);
    }

    @Test
    public void testDiscountGivenForSpecificUserNumber() {
        discountService.getDiscount(user, new Event(), LocalDateTime.now(), 11);
        discountService.getDiscount(user, new Event(), LocalDateTime.now(), 9);
        discountService.getDiscount(user1, new Event(), LocalDateTime.now(), 9);

        Map<User, Integer> callsForSpecificUser = discountAspect.getCallsForSpecificUser();
        long callsNumber = callsForSpecificUser.get(user);

        assertEquals(callsNumber, 4);
    }

}
