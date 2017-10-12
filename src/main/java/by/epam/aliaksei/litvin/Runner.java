package by.epam.aliaksei.litvin;

import by.epam.aliaksei.litvin.aspects.DiscountAspect;
import by.epam.aliaksei.litvin.config.AppConfig;
import by.epam.aliaksei.litvin.domain.Event;
import by.epam.aliaksei.litvin.domain.User;
import by.epam.aliaksei.litvin.strategies.impl.BirthdayDiscountStrategy;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Runner {

    public static void main(String[] args) {

        User user = new User();
        user.setBirthday(LocalDate.now());

        AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        BirthdayDiscountStrategy bean = ctx.getBean(BirthdayDiscountStrategy.class);
        bean.getDiscountValue(null, null, null, 1);
        bean.getDiscountValue(user, new Event(), LocalDateTime.now(), 1);
        bean.getDiscountValue(null, null, null, 1);
        DiscountAspect bean1 = ctx.getBean(DiscountAspect.class);
        System.out.println(bean1.getCallsCounter());
    }
}
