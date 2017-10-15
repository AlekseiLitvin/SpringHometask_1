package by.epam.aliaksei.litvin;

import by.epam.aliaksei.litvin.config.AppConfig;
import by.epam.aliaksei.litvin.domain.*;
import by.epam.aliaksei.litvin.service.BookingService;
import by.epam.aliaksei.litvin.service.UserService;
import by.epam.aliaksei.litvin.service.impl.BookingServiceImpl;
import by.epam.aliaksei.litvin.service.impl.UserServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class Runner {

    public static void main(String[] args) {

        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        UserService userService = ctx.getBean(UserServiceImpl.class);
        JdbcTemplate jdbcTemplate = ctx.getBean(JdbcTemplate.class);

        User user = new User();
        user.setEmail("test");
        user.setBirthday(LocalDate.now());

        userService.save(user);
        User test = userService.getUserByEmail("test");
        System.out.println(test);

        System.out.println(userService.getById(test.getId()));

    }
}
