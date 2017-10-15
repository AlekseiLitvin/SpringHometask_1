package by.epam.aliaksei.litvin.service.impl;

import by.epam.aliaksei.litvin.domain.Event;
import by.epam.aliaksei.litvin.domain.User;
import by.epam.aliaksei.litvin.service.EventService;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class EventServiceImpl implements EventService {

    private JdbcTemplate jdbcTemplate;
    private List<Event> events;

    public EventServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void init() {
        jdbcTemplate.update("CREATE TABLE events (\n" +
                "id VARCHAR(50) PRIMARY KEY,\n" +
                "name VARCHAR(30),\n" +
                "airDates SET,\n" +
                "email  VARCHAR(50),\n" +
                "birthday  DATE\n" +
                ")");
    }


    @Nullable
    @Override
    public Event getByName(@Nonnull String name) {
        return events.stream()
                .filter(event -> event.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    @Nonnull
    @Override
    public Set<Event> getForDateRange(@Nonnull LocalDate from, @Nonnull LocalDate to) {
        return events.stream()
                .filter(event -> event.airsOnDates(from, to))
                .collect(Collectors.toSet());
    }

    @Nonnull
    @Override
    public Set<Event> getNextEvents(@Nonnull LocalDateTime to) {
        Set<Event> result = new HashSet<>();
        events.forEach(event -> {
            NavigableSet<LocalDateTime> airDates = event.getAirDates().headSet(to, true);
            event.setAirDates(airDates);
            result.add(event);
        });
        return result;
    }

    @Override
    public Event save(@Nonnull Event object) {
        return object;
    }

    @Override
    public void remove(@Nonnull Event object) {
        events.remove(object);
    }

    @Override
    public Event getById(@Nonnull String id) {
        return events.stream()
                .filter(event -> event.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Nonnull
    @Override
    public Collection<Event> getAll() {
        return events;
    }

    @Override
    public void removeAll() {
        events.clear();
    }

    private Event getByField(String fieldName, String fieldValue) {
        Event event = null;
        List<Map<String, Object>> users = jdbcTemplate.queryForList("SELECT * FROM events WHERE " + fieldName + " = ?", fieldValue);
        if (!users.isEmpty()) {
            event = new Event();
            Map<String, Object> userAttributes = users.get(0);
            event.setId((String) userAttributes.get("ID"));
            //TODO
        }
        return event;
    }
}
