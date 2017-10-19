package by.epam.aliaksei.litvin.daos.impl;

import by.epam.aliaksei.litvin.daos.AbstractDomainObjectDao;
import by.epam.aliaksei.litvin.domain.Event;
import by.epam.aliaksei.litvin.domain.Ticket;
import by.epam.aliaksei.litvin.domain.User;
import by.epam.aliaksei.litvin.service.EventService;
import by.epam.aliaksei.litvin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Date;
import java.util.*;

public class TicketsDaoImpl implements AbstractDomainObjectDao<Ticket> {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private EventService eventService;

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
    public Ticket save(Ticket ticket) {
        jdbcTemplate.update("INSERT INTO tickets(id, userId, eventId, date, seat) VALUES (?, ?, ?, ?, ?)",
                UUID.randomUUID().toString(),
                ticket.getUser().getId(),
                ticket.getEvent().getId(),
                Date.valueOf(ticket.getDateTime().toLocalDate()),
                ticket.getSeat());
        return ticket;
    }

    @Override
    public void remove(Ticket object) {
        jdbcTemplate.update("DELETE FROM tickets WHERE id = ?", object.getId());
    }

    @Override
    public Ticket getById(String id) {
        Ticket ticket = null;
        List<Map<String, Object>> tickets = jdbcTemplate.queryForList("SELECT * FROM events WHERE id = ?", id);
        if (!tickets.isEmpty()) {
            Map<String, Object> ticketsAttributes = tickets.get(0);
            String userId = (String) ticketsAttributes.get("userId");
            User user = userService.getById(userId);
            String eventId = (String) ticketsAttributes.get("eventId");
            Event event = eventService.getById(eventId);
            Date date = (Date)ticketsAttributes.get("date");
            long seat = (long) ticketsAttributes.get("seat");
            ticket = new Ticket(user, event, date.toLocalDate().atStartOfDay(), seat);
            ticket.setId(id);
        }
        return ticket;
    }

    @Override
    public Collection<Ticket> getAll() {
        List<Ticket> result = new ArrayList<>();
        List<Map<String, Object>> events = jdbcTemplate.queryForList("SELECT * FROM tickets");
        events.forEach(it -> {
            String id = (String) it.get("userId");
            String userId = (String) it.get("userId");
            User user = userService.getById(userId);
            String eventId = (String) it.get("eventId");
            Event event = eventService.getById(eventId);
            Date date = (Date)it.get("date");
            long seat = (long) it.get("seat");
            Ticket ticket = new Ticket(user, event, date.toLocalDate().atStartOfDay(), seat);
            ticket.setId(id);
            result.add(ticket);
        });
        return result;
    }

    @Override
    public void removeAll() {
        jdbcTemplate.update("DELETE FROM tickets");
    }
}
