package by.epam.aliaksei.litvin.daos.impl;

import by.epam.aliaksei.litvin.daos.AbstractDomainObjectDao;
import by.epam.aliaksei.litvin.domain.Event;
import by.epam.aliaksei.litvin.domain.EventRating;
import by.epam.aliaksei.litvin.domain.Ticket;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.*;

public class TicketsDaoImpl implements AbstractDomainObjectDao<Ticket> {

    private JdbcTemplate jdbcTemplate;

    public TicketsDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void init() {
        jdbcTemplate.update("CREATE TABLE tickets (\n" +
                "id VARCHAR(50) PRIMARY KEY,\n" +
                "userId VARCHAR(50) ,\n" +
                "eventId VARCHAR(50) ,\n" +
                "date DATE,\n" +
                "seat BIGINT)");
    }

    @Override
    public Ticket save(Ticket object) {//TODO
        jdbcTemplate.update("INSERT INTO tickets(id, userId, ticketId, date, seat) VALUES ( ?, ?, ?, ?, ?)",
                UUID.randomUUID().toString(), object.getName(), object.getBasePrice(), object.getRating().name(),);
        return object;
    }

    @Override
    public void remove(Ticket object) {
        jdbcTemplate.update("DELETE FROM tickets WHERE id = ?", object.getId());
    }

    @Override
    public Ticket getById(String id) {
        return null;
    }

    @Override
    public Collection<Ticket> getAll() {
        List<Ticket> result = new ArrayList<>();
        List<Map<String, Object>> events = jdbcTemplate.queryForList("SELECT * FROM tickets");
        events.forEach(it -> {
            Event event = new Event();
            event.setId((String) it.get("ID"));
            event.setName((String) it.get("userId"));
            event.setBasePrice((Double) it.get("basePrice"));
            event.setRating(EventRating.valueOf((String) it.get("eventRating")));
            result.add(event);
        });
        return result;
    }

    @Override
    public void removeAll() {
        jdbcTemplate.update("DELETE FROM tickets");
    }
}
