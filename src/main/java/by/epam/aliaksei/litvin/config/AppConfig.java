package by.epam.aliaksei.litvin.config;


import by.epam.aliaksei.litvin.service.AuditoriumService;
import by.epam.aliaksei.litvin.service.BookingService;
import by.epam.aliaksei.litvin.service.EventService;
import by.epam.aliaksei.litvin.service.UserService;
import by.epam.aliaksei.litvin.service.impl.AuditoriumServiceImpl;
import by.epam.aliaksei.litvin.service.impl.BookingServiceImpl;
import by.epam.aliaksei.litvin.service.impl.EventServiceImpl;
import by.epam.aliaksei.litvin.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.util.ArrayList;

@Configuration
@ComponentScan("by.epam.aliaksei.litvin")
@PropertySource("app.properties")
public class AppConfig {

    @Autowired
    private Environment env;

    @Bean
    public UserService userService() {
        return new UserServiceImpl(new ArrayList<>());
    }

    @Bean(initMethod = "init")
    public AuditoriumService auditoriumService() {
        return new AuditoriumServiceImpl(env.getProperty("auditoriums.filename"));
    }

    @Bean
    public EventService eventService() {
        return new EventServiceImpl(new ArrayList<>());
    }

    @Bean
    public BookingService bookingService() {
        return new BookingServiceImpl();
    }

}
