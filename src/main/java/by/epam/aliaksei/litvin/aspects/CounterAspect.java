package by.epam.aliaksei.litvin.aspects;

import by.epam.aliaksei.litvin.domain.Event;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;

import java.util.Map;

@Aspect
public class CounterAspect {

    private Map<Event, Integer> eventsAccessedByName;
    private Map<Event, Integer> priceQueriedNumbers;


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

    @After("execution(* by.epam.aliaksei.litvin.service.impl.BookingServiceImpl.bookTickets(java.util.Set<by.epam.aliaksei.litvin.domain.Ticket>))")
    public void bookedeTicketsCounter() {
        System.out.println("WORK");
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
}
