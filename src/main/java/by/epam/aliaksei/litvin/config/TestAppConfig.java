package by.epam.aliaksei.litvin.config;

import by.epam.aliaksei.litvin.aspects.CounterAspect;
import by.epam.aliaksei.litvin.aspects.DiscountAspect;
import by.epam.aliaksei.litvin.service.AuditoriumService;
import by.epam.aliaksei.litvin.service.BookingService;
import by.epam.aliaksei.litvin.service.UserService;
import by.epam.aliaksei.litvin.service.impl.AuditoriumServiceImpl;
import by.epam.aliaksei.litvin.service.impl.BookingServiceImpl;
import by.epam.aliaksei.litvin.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.ArrayList;
import java.util.HashMap;

@Configuration
@ComponentScan("by.epam.aliaksei.litvin")
@PropertySource("app.properties")
@Import(DiscountsConfig.class)
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class TestAppConfig {

    @Autowired
    private Environment env;

    @Bean(initMethod = "init")
    public UserServiceImpl userService() {
        return new UserServiceImpl(jdbcTemplate());
    }

    @Bean(initMethod = "init")
    public AuditoriumServiceImpl auditoriumService() {
        return new AuditoriumServiceImpl(env.getProperty("auditoriums.filename"));
    }

    @Bean
    public BookingService bookingService() {
        BookingServiceImpl bookingService = new BookingServiceImpl();
        bookingService.setVipSeatModifier(3);
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
