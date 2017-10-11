package by.epam.aliaksei.litvin.services;


import by.epam.aliaksei.litvin.config.DiscountsConfig;
import by.epam.aliaksei.litvin.config.TestAppConfig;
import by.epam.aliaksei.litvin.domain.Event;
import by.epam.aliaksei.litvin.domain.Ticket;
import by.epam.aliaksei.litvin.domain.User;
import by.epam.aliaksei.litvin.service.impl.DiscountServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DiscountsConfig.class, loader = AnnotationConfigContextLoader.class)
public class DiscountServiceImplTest {

    private Event event;
    private User user;
    private LocalDateTime localDate;

    @Autowired
    private DiscountServiceImpl discountService;


    @Before
    public void setUp() {
        event = new Event();
        localDate = LocalDateTime.now();

        user = new User();
        user.setBirthday(localDate.toLocalDate().plusDays(2));
    }

    @Test
    public void test_getDiscount() {
        double discount1 = discountService.getDiscount(user, event, localDate, 9);
        Assert.assertEquals(discount1, 0.3, 0);

        user.setBirthday(LocalDate.now().plusWeeks(10));
        double discount2 = discountService.getDiscount(user, event, localDate, 9);
        Assert.assertEquals(discount2, 0.0, 0);

        double discount3 = discountService.getDiscount(user, event, localDate, 11);
        Assert.assertEquals(discount3, 0.2, 0);
    }

}
