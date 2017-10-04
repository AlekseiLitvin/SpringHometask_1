package by.epam.aliaksei.litvin.service.impl;

import by.epam.aliaksei.litvin.domain.Event;
import by.epam.aliaksei.litvin.service.EventService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class EventServiceImpl implements EventService{

    private List<Event> events;

    public EventServiceImpl(List<Event> events) {
        this.events = events;
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
        object.setId((long) events.size());
        events.add(object);
        return object;
    }

    @Override
    public void remove(@Nonnull Event object) {
        events.remove(object);
    }

    @Override
    public Event getById(@Nonnull Long id) {
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
}
