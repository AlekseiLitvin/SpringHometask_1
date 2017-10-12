package by.epam.aliaksei.litvin;

import by.epam.aliaksei.litvin.aspects.CounterAspect;
import by.epam.aliaksei.litvin.aspects.DiscountAspect;
import by.epam.aliaksei.litvin.config.AppConfig;
import by.epam.aliaksei.litvin.domain.Event;
import by.epam.aliaksei.litvin.domain.User;
import by.epam.aliaksei.litvin.service.EventService;
import by.epam.aliaksei.litvin.service.impl.EventServiceImpl;
import by.epam.aliaksei.litvin.strategies.impl.BirthdayDiscountStrategy;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Runner {

    public static void main(String[] args) {

        Event event = new Event();
        event.setName("test");

        AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        EventService bean = ctx.getBean(EventServiceImpl.class);
        bean.save(event);
        bean.getByName("test");

        CounterAspect bean1 = ctx.getBean(CounterAspect.class);
        System.out.println(bean1.getEventsAccessedByName());
    }
}
