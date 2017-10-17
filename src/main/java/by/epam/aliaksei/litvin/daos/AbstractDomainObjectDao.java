package by.epam.aliaksei.litvin.daos;

import by.epam.aliaksei.litvin.domain.DomainObject;

import java.util.Collection;

public interface AbstractDomainObjectDao<T extends DomainObject> {
    T save(T object);
    void remove(T object);
    T getById(String id);
    Collection<T> getAll();
    void removeAll();
}
