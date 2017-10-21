package by.epam.aliaksei.litvin.daos;

import java.util.Map;

public interface CounterDao {

    void saveCallsCounter(String className, int count);
    Map<String, Integer> getAllCallsCounter();

    void saveCallForSpecificUser(String userId, int count);
    Map<String, Integer> getAllCallsForSpecificUser();

    void saveEventAccessedByName(String eventId, int count);
    Map<String, Integer> getAllEventsAccessedByName();

    void savePriceQueriedNumbers(String eventId, int count);
    Map<String, Integer> getAllPricesQueriedNumbers();

    void saveTicketsBookedCounter(String eventId, int count);
    Map<String, Integer> getAllTicketsBookedCounters();

}
