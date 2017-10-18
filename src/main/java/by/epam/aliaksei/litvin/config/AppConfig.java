package by.epam.aliaksei.litvin.config;


import by.epam.aliaksei.litvin.aspects.CounterAspect;
import by.epam.aliaksei.litvin.aspects.DiscountAspect;
import by.epam.aliaksei.litvin.daos.EventDao;
import by.epam.aliaksei.litvin.daos.impl.EventDaoImpl;
import by.epam.aliaksei.litvin.daos.impl.TicketsDaoImpl;
import by.epam.aliaksei.litvin.daos.impl.UserDaoImpl;
import by.epam.aliaksei.litvin.domain.Event;
import by.epam.aliaksei.litvin.service.AuditoriumService;
import by.epam.aliaksei.litvin.service.BookingService;
import by.epam.aliaksei.litvin.service.EventService;
import by.epam.aliaksei.litvin.service.UserService;
import by.epam.aliaksei.litvin.service.impl.AuditoriumServiceImpl;
import by.epam.aliaksei.litvin.service.impl.BookingServiceImpl;
import by.epam.aliaksei.litvin.service.impl.EventServiceImpl;
import by.epam.aliaksei.litvin.service.impl.UserServiceImpl;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;

@Configuration
@ComponentScan("by.epam.aliaksei.litvin")
@PropertySource("app.properties")
@Import(DiscountsConfig.class)
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AppConfig {

    @Autowired
    private Environment env;

    @Bean(initMethod = "init")
    public UserDaoImpl userDao() {
        return new UserDaoImpl(jdbcTemplate());
    }
    @Bean
    public UserServiceImpl userService() {
        return new UserServiceImpl(userDao());
    }

    @Bean(initMethod = "init")
    public EventDaoImpl eventDao() {
        return new EventDaoImpl(jdbcTemplate());
    }

    @Bean
    public EventServiceImpl eventService() {
        return new EventServiceImpl(eventDao());
    }

    @Bean(initMethod = "init")
    public TicketsDaoImpl ticketsDao() {
        return new TicketsDaoImpl(jdbcTemplate());
    }

    @Bean(initMethod = "init")
    public AuditoriumServiceImpl auditoriumService() {
        return new AuditoriumServiceImpl(env.getProperty("auditoriums.filename"));
    }

    @Bean
    public BookingService bookingService() {
        BookingServiceImpl bookingService = new BookingServiceImpl();
        bookingService.setUserService(userService());
        return bookingService;
    }

    @Bean
    public DiscountAspect discountAspect() {
        return new DiscountAspect(new HashMap<>(), new HashMap<>());
    }

    @Bean
    public CounterAspect counterAspect() {
        CounterAspect counterAspect = new CounterAspect();
        counterAspect.setEventsAccessedByName(new HashMap<>());
        counterAspect.setPriceQueriedNumbers(new HashMap<>());
        counterAspect.setTicketsBookedCounter(new HashMap<>());
        return counterAspect;
    }

    @Bean
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("driverClassName"));
        dataSource.setUrl(env.getProperty("db.url"));
        dataSource.setUsername("username");
        dataSource.setPassword("password");
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

}
