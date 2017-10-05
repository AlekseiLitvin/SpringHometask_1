package by.epam.aliaksei.litvin.strategies.impl;

import by.epam.aliaksei.litvin.domain.Event;
import by.epam.aliaksei.litvin.domain.User;
import by.epam.aliaksei.litvin.strategies.DiscountStrategy;

import java.time.LocalDateTime;

public class EveryNTicketDiscountStrategy implements DiscountStrategy{

    private int ticketsForDiscountNumber;

    @Override
    public double getDiscountValue(User user, Event event, LocalDateTime airDateTime, long numberOfTickets) {
        if (numberOfTickets > ticketsForDiscountNumber) {
            return 0.2;
        }
        return 0.0;
    }

    public int getTicketsForDiscountNumber() {
        return ticketsForDiscountNumber;
    }

    public void setTicketsForDiscountNumber(int ticketsForDiscountNumber) {
        this.ticketsForDiscountNumber = ticketsForDiscountNumber;
    }
}
