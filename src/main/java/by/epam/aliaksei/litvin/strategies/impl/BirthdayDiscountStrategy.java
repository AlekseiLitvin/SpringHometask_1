package by.epam.aliaksei.litvin.strategies.impl;

import by.epam.aliaksei.litvin.domain.Event;
import by.epam.aliaksei.litvin.domain.User;
import by.epam.aliaksei.litvin.strategies.DiscountStrategy;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class BirthdayDiscountStrategy implements DiscountStrategy{

    private static final int DAYS_FROM_AIR_DATE = 5;

    @Override
    public double getDiscountValue(User user, Event event, LocalDateTime airDateTime, long numberOfTickets) {
        if (user != null) {
            LocalDate birthday = user.getBirthday();
            if (birthday.isAfter(airDateTime.toLocalDate())
                    && birthday.isBefore(airDateTime.toLocalDate().plusDays(DAYS_FROM_AIR_DATE))) {
                return 0.3;
            }
        }
        return 0.0;
    }
}
