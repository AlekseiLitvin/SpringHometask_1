package by.epam.aliaksei.litvin.strategies;

import by.epam.aliaksei.litvin.domain.Event;
import by.epam.aliaksei.litvin.domain.User;

import java.time.LocalDateTime;

public interface DiscountStrategy {

    /**
     * Applies discount based on criteria
     */
    double getDiscountValue(User user, Event event, LocalDateTime airDateTime, long numberOfTickets);

}
