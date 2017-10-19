package by.epam.aliaksei.litvin.daos.impl;

import by.epam.aliaksei.litvin.daos.EventDao;
import by.epam.aliaksei.litvin.domain.Event;
import by.epam.aliaksei.litvin.domain.EventRating;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class EventDaoImpl implements EventDao {

    private JdbcTemplate jdbcTemplate;

    public EventDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void init() {
        jdbcTemplate.update("CREATE TABLE events (\n" +
                "id VARCHAR(50) PRIMARY KEY,\n" +
                "name VARCHAR(30),\n" +
                "basePrice DOUBLE,\n" +
                "eventRating  VARCHAR(50))");
    }

    @Nullable
    @Override
    public Event getByName(@Nonnull String name) {
        return getByField("name", name);
    }

    @Nonnull
    @Override
    public Set<Event> getForDateRange(@Nonnull LocalDate from, @Nonnull LocalDate to) {
        List<Event> events = getAll();
        return events.stream()
                .filter(event -> event.airsOnDates(from, to))
                .collect(Collectors.toSet());
}

    @Nonnull
    @Override
    public Set<Event> getNextEvents(@Nonnull LocalDateTime to) {
        List<Event> events = getAll();
        Set<Event> result = new HashSet<>();
        events.forEach(event -> {
            NavigableSet<LocalDateTime> airDates = event.getAirDates().headSet(to, true);
            event.setAirDates(airDates);
            result.add(event);
        });
        return result;
    }

    @Override
    public Event save(Event object) {
        jdbcTemplate.update("INSERT INTO events(id, name, basePrice, eventRating) VALUES ( ?, ?, ?, ?)",
                UUID.randomUUID().toString(), object.getName(), object.getBasePrice(), object.getRating().name());
        return object;
    }

    @Override
    public void remove(Event object) {
        jdbcTemplate.update("DELETE FROM events WHERE id = ?", object.getId());
    }

    @Override
    public Event getById(String id) {
        return getByField("id", id);
    }

    @Override
    public List<Event> getAll() {
        List<Event> result = new ArrayList<>();
        List<Map<String, Object>> events = jdbcTemplate.queryForList("SELECT * FROM events");
        events.forEach(it -> {
            Event event = new Event();
            event.setId((String) it.get("ID"));
            event.setName((String) it.get("name"));
            event.setBasePrice((Double) it.get("basePrice"));
            event.setRating(EventRating.valueOf((String) it.get("eventRating")));
            result.add(event);
        });
        return result;
    }

    @Override
    public void removeAll() {
        jdbcTemplate.update("DELETE FROM events");
    }

    private Event getByField(String fieldName, String fieldValue) {
        Event event = null;
        List<Map<String, Object>> events = jdbcTemplate.queryForList("SELECT * FROM events WHERE " + fieldName + " = ?", fieldValue);
        if (!events.isEmpty()) {
            event = new Event();
            Map<String, Object> eventAttributes = events.get(0);
            event.setId((String) eventAttributes.get("ID"));
            event.setName((String) eventAttributes.get("NAME"));
            event.setBasePrice((Double) eventAttributes.get("basePrice"));
            event.setRating(EventRating.valueOf((String) eventAttributes.get("eventRating")));
        }
        return event;
    }
}
