package by.epam.aliaksei.litvin.aspects;

import by.epam.aliaksei.litvin.domain.Event;
import by.epam.aliaksei.litvin.domain.Ticket;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import java.util.Map;
import java.util.Set;

@Aspect
public class CounterAspect {

    private Map<Event, Integer> eventsAccessedByName;
    private Map<Event, Integer> priceQueriedNumbers;
    private Map<Event, Integer> ticketsBookedCounter;


    @AfterReturning(pointcut = "execution(by.epam.aliaksei.litvin.domain.Event by.epam.aliaksei.litvin.service.impl.*.getByName(..))", returning = "event")
    public void accessByName(Event event) {
        if (eventsAccessedByName.containsKey(event)) {
            Integer numberOfCalls = eventsAccessedByName.get(event);
            eventsAccessedByName.put(event, numberOfCalls + 1);
        } else {
            eventsAccessedByName.put(event, 1);
        }
    }

    @After("execution(* by.epam.aliaksei.litvin.service.impl.BookingServiceImpl.getTicketsPrice(..))")
    public void priceQueriedCounter(JoinPoint joinPoint) {
        Event event = (Event) joinPoint.getArgs()[0];
        if (priceQueriedNumbers.containsKey(event)) {
            Integer numberOfCalls = priceQueriedNumbers.get(event);
            priceQueriedNumbers.put(event, numberOfCalls + 1);
        } else {
            priceQueriedNumbers.put(event, 1);
        }
    }

    @SuppressWarnings("unchecked")
    @Before("execution(* by.epam.aliaksei.litvin.service.impl.BookingServiceImpl.bookTickets(java.util.Set))")
    public void bookedTicketsCounter(JoinPoint joinPoint) {
        Set<Ticket> tickets = (Set<Ticket>) joinPoint.getArgs()[0];{
            tickets.forEach(ticket -> {
                Event event = ticket.getEvent();
                if (ticketsBookedCounter.containsKey(event)) {
                    Integer numberOfCalls = ticketsBookedCounter.get(event);
                    ticketsBookedCounter.put(event, numberOfCalls + 1);
                } else {
                    ticketsBookedCounter.put(event, 1);
                }
            });
        }
    }

    public Map<Event, Integer> getEventsAccessedByName() {
        return eventsAccessedByName;
    }

    public void setEventsAccessedByName(Map<Event, Integer> eventsAccessedByName) {
        this.eventsAccessedByName = eventsAccessedByName;
    }

    public Map<Event, Integer> getPriceQueriedNumbers() {
        return priceQueriedNumbers;
    }

    public void setPriceQueriedNumbers(Map<Event, Integer> priceQueriedNumbers) {
        this.priceQueriedNumbers = priceQueriedNumbers;
    }

    public Map<Event, Integer> getTicketsBookedCounter() {
        return ticketsBookedCounter;
    }

    public void setTicketsBookedCounter(Map<Event, Integer> ticketsBookedCounter) {
        this.ticketsBookedCounter = ticketsBookedCounter;
    }
}
