package by.epam.aliaksei.litvin.daos.impl;

import by.epam.aliaksei.litvin.daos.CounterDao;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CounterDaoImpl implements CounterDao {

    private JdbcTemplate jdbcTemplate;

    public CounterDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private void init() {
        jdbcTemplate.update("CREATE TABLE callsCounter (id not null primary key GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
                "name VARCHAR(50)," +
                "count INT)");
        jdbcTemplate.update("CREATE TABLE callsForSpecificUser (id not null primary key GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
                "userId VARCHAR(50)," +
                "count INT)");
        jdbcTemplate.update("CREATE TABLE eventsAccessedByName (id not null primary key GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
                "name VARCHAR(50)," +
                "count INT)");
        jdbcTemplate.update("CREATE TABLE priceQueriedNumbers (id not null primary key GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
                "name VARCHAR(50)," +
                "count INT)");
        jdbcTemplate.update("CREATE TABLE ticketsBookedCounter (id not null primary key GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
                "name VARCHAR(50)," +
                "count INT)");

    }

    public void saveCallsCounter(String className, int count) {
        jdbcTemplate.update("INSERT INTO callsCounter(name, count) VALUES (?, ?)", className, className);
    }

    public Map<String, Integer> getAllCallsCounter() {
        Map<String, Integer> result = new HashMap<>();
        List<Map<String, Object>> events = jdbcTemplate.queryForList("SELECT * FROM callsCounter");
        events.forEach(it -> {
            String name = (String) it.get("name");
            Integer count = (Integer) it.get("count");
            result.put(name, count);
        });
        return result;
    }

    public void saveCallForSpecificUser(String userId, int count) {
        jdbcTemplate.update("INSERT INTO callsForSpecificUser(userId, count) VALUES (?, ?)", userId, count);
    }

    public Map<String, Integer> getAllCallsForSpecificUser() {
        Map<String, Integer> result = new HashMap<>();
        List<Map<String, Object>> events = jdbcTemplate.queryForList("SELECT * FROM callsForSpecificUser");
        events.forEach(it -> {
            String name = (String) it.get("userId");
            Integer count = (Integer) it.get("count");
            result.put(name, count);
        });
        return result;
    }

    public void saveEventAccessedByName(String eventId, int count) {
        saveEventCounter("callsForSpecificUser", eventId, count);
    }

    public Map<String, Integer> getAllEventsAccessedByName() {
        return getAllEventCounters("eventsAccessedByName");
    }

    public void savePriceQueriedNumbers(String eventId, int count) {
        saveEventCounter("priceQueriedNumbers", eventId, count);
    }

    public Map<String, Integer> getAllPricesQueriedNumbers() {
        return getAllEventCounters("priceQueriedNumbers");
    }

    public void saveTicketsBookedCounter(String eventId, int count) {
        saveEventCounter("ticketsBookedCounter", eventId, count);
    }

    public Map<String, Integer> getAllTicketsBookedCounters() {
        return getAllEventCounters("ticketsBookedCounter");
    }

    private void saveEventCounter(String tableName, String eventId, int count) {
        jdbcTemplate.update("INSERT INTO " + tableName + "(userId, count) VALUES (?, ?)", eventId, count);
    }

    private Map<String, Integer> getAllEventCounters(String tableName) {
        Map<String, Integer> result = new HashMap<>();
        List<Map<String, Object>> events = jdbcTemplate.queryForList("SELECT * FROM " + tableName);
        events.forEach(it -> {
            String name = (String) it.get("eventId");
            Integer count = (Integer) it.get("count");
            result.put(name, count);
        });
        return result;
    }


}
