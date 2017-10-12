package by.epam.aliaksei.litvin.aspects;

import by.epam.aliaksei.litvin.domain.Event;
import by.epam.aliaksei.litvin.domain.User;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import java.util.Map;

@Aspect
public class CounterAspect {

    private Map<Event, Integer> eventsAccessedByName;

    public CounterAspect(Map<Event, Integer> eventsAccessedByName) {
        this.eventsAccessedByName = eventsAccessedByName;
    }

    public CounterAspect() {
    }

    @AfterReturning(pointcut = "execution(by.epam.aliaksei.litvin.domain.Event by.epam.aliaksei.litvin.service.impl.*.getByName(..))", returning = "event")
    public void accessByName(Event event) {
        if (eventsAccessedByName.containsKey(event)) {
            Integer numberOfCalls = eventsAccessedByName.get(event);
            eventsAccessedByName.put(event, numberOfCalls + 1);
        } else {
            eventsAccessedByName.put(event, 1);
        }
    }

    @Before("execution(* by.epam.aliaksei.litvin.domain.Event.getBasePrice())")
    public void priceQueriedCounter() {
        //TODO
    }

    public Map<Event, Integer> getEventsAccessedByName() {
        return eventsAccessedByName;
    }

    public void setEventsAccessedByName(Map<Event, Integer> eventsAccessedByName) {
        this.eventsAccessedByName = eventsAccessedByName;
    }

}
