package by.epam.aliaksei.litvin.service.impl;

import by.epam.aliaksei.litvin.domain.*;
import by.epam.aliaksei.litvin.service.BookingService;
import by.epam.aliaksei.litvin.service.UserService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class BookingServiceImpl implements BookingService {

    private int vipSeatModifier;

    private UserService userService;

    @Override
    public double getTicketsPrice(@Nonnull Event event, @Nonnull LocalDateTime dateTime, @Nullable User user, @Nonnull Set<Long> seats) {
        double basePrice = event.getBasePrice();
        double ratingModifier = getPriceModifierBasedOnEventRating(event.getRating());
        Auditorium auditorium = event.getAuditoriums().get(dateTime);
        int vipSeatsNumber = (int) auditorium.countVipSeats(seats);
        int regularSeatsNumber = seats.size() - vipSeatsNumber;
        return basePrice * ratingModifier * regularSeatsNumber + basePrice * ratingModifier * vipSeatModifier * vipSeatsNumber;
    }

    @Override
    public void bookTickets(@Nonnull Set<Ticket> tickets) {
        tickets.forEach(ticket -> userService.save(ticket.getUser()));
    }

    @Nonnull
    @Override
    public Set<Ticket> getPurchasedTicketsForEvent(@Nonnull Event event, @Nonnull LocalDateTime dateTime) {
        Set<Ticket> purchasedTickets = new HashSet<>();
        userService.getAll().forEach(user -> {
            Set<Ticket> tickets = user.getTickets().stream()
                    .filter(ticket -> ticket.getEvent().equals(event))
                    .filter(ticket -> ticket.getDateTime().equals(dateTime))
                    .collect(Collectors.toSet());
            purchasedTickets.addAll(tickets);
        });
        return purchasedTickets;
    }


    private double getPriceModifierBasedOnEventRating(EventRating rating) {
        switch (rating) {
            case HIGH:
                return 1.2;
            case MID:
                return 1;
            case LOW:
                return 0.8;
            default:
                throw new IllegalArgumentException("Illegal rating value");
        }
    }

    public int getVipSeatModifier() {
        return vipSeatModifier;
    }

    public void setVipSeatModifier(int vipSeatModifier) {
        this.vipSeatModifier = vipSeatModifier;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
