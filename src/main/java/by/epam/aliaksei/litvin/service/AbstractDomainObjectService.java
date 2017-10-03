package by.epam.aliaksei.litvin.service;



import by.epam.aliaksei.litvin.domain.DomainObject;

import javax.annotation.Nonnull;
import java.util.Collection;

/**
 * @author Yuriy_Tkach
 *
 * @param <T>
 *            DomainObject subclass
 */
public interface AbstractDomainObjectService<T extends DomainObject> {

    /**
     * Saving new object to storage or updating existing one
     * 
     * @param object
     *            Object to save
     */
    T save(@Nonnull T object);

    /**
     * Removing object from storage
     * 
     * @param object
     *            Object to remove
     */
    void remove(@Nonnull T object);

    /**
     * Getting object by id from storage
     * 
     * @param id
     *            id of the object
     * @return Found object or <code>null</code>
     */
    T getById(@Nonnull Long id);

    /**
     * Getting all objects from storage
     * 
     * @return collection of objects
     */
    @Nonnull Collection<T> getAll();
}
