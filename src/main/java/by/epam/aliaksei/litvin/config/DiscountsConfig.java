package by.epam.aliaksei.litvin.config;

import by.epam.aliaksei.litvin.service.DiscountService;
import by.epam.aliaksei.litvin.service.impl.DiscountServiceImpl;
import by.epam.aliaksei.litvin.strategies.DiscountStrategy;
import by.epam.aliaksei.litvin.strategies.impl.BirthdayDiscountStrategy;
import by.epam.aliaksei.litvin.strategies.impl.EveryNTicketDiscountStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ComponentScan("by.epam.aliaksei.litvin")
@PropertySource("app.properties")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class DiscountsConfig {

    @Autowired
    private Environment env;

    @Bean(name = "everyNTicketDiscountStrategy")
    public DiscountStrategy everyNTicketDiscountStrategy() {
        EveryNTicketDiscountStrategy discountStrategy = new EveryNTicketDiscountStrategy();
        discountStrategy.setTicketsForDiscountNumber(Integer.parseInt(env.getProperty("discount.tickets.number")));
        return discountStrategy;
    }

    @Bean(name = "birthdayDiscountStrategy")
    public DiscountStrategy birthdayDiscountStrategy() {
        return new BirthdayDiscountStrategy();
    }

    @Bean
    public DiscountService discountService() {
        List<DiscountStrategy> discounts = new ArrayList<>();
        discounts.add(everyNTicketDiscountStrategy());
        discounts.add(birthdayDiscountStrategy());
        return new DiscountServiceImpl(discounts);
    }

}
