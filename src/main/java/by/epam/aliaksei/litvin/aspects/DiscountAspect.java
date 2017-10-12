package by.epam.aliaksei.litvin.aspects;


import by.epam.aliaksei.litvin.domain.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import java.util.Map;

@Aspect
public class DiscountAspect {

    private Map<Class, Integer> callsCounter;
    private Map<User, Integer> callsForSpecificUser;

    @Before("execution(* by.epam.aliaksei.litvin.strategies.impl.*.getDiscountValue(..))")
    public void getGivenDiscountNumber(JoinPoint joinPoint) {
        Class discountStrategy = joinPoint.getThis().getClass();
        if (callsCounter.containsKey(discountStrategy)) {
            Integer numberOfCalls = callsCounter.get(discountStrategy);
            callsCounter.put(discountStrategy, numberOfCalls + 1);
        } else {
            callsCounter.put(discountStrategy, 1);
        }
    }

    @Before("execution(* by.epam.aliaksei.litvin.strategies.impl.*.getDiscountValue(by.epam.aliaksei.litvin.domain.User, by.epam.aliaksei.litvin.domain.Event, java.time.LocalDateTime, long))")
    public void getDiscountCallsForSpecificUser(JoinPoint joinPoint) {
        User user = (User) joinPoint.getArgs()[0];
        if (callsForSpecificUser.containsKey(user)) {
            Integer numberOfCalls = callsForSpecificUser.get(user);
            callsForSpecificUser.put(user, numberOfCalls + 1);
        } else {
            callsForSpecificUser.put(user, 1);
        }
    }

    public DiscountAspect(Map<Class, Integer> callsCounter, Map<User, Integer> callsForSpecificUser) {
        this.callsCounter = callsCounter;
        this.callsForSpecificUser = callsForSpecificUser;
    }

    public Map<Class, Integer> getCallsCounter() {
        return callsCounter;
    }

    public void setCallsCounter(Map<Class, Integer> callsCounter) {
        this.callsCounter = callsCounter;
    }

    public Map<User, Integer> getCallsForSpecificUser() {
        return callsForSpecificUser;
    }

    public void setCallsForSpecificUser(Map<User, Integer> callsForSpecificUser) {
        this.callsForSpecificUser = callsForSpecificUser;
    }
}

