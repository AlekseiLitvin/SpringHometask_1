package by.epam.aliaksei.litvin.service.impl;

import by.epam.aliaksei.litvin.domain.User;
import by.epam.aliaksei.litvin.service.UserService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;

public class UserServiceImpl implements UserService {

    private List<User> users;

    public UserServiceImpl(List<User> users) {
        this.users = users;
    }

    @Nullable
    @Override
    public User getUserByEmail(@Nonnull String email) {
        return users.stream()
                .filter(user -> user.getEmail() != null)
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public User save(@Nonnull User object) {
        object.setId((long) users.size());
        users.add(object);
        return object;
    }

    @Override
    public void remove(@Nonnull User object) {
        users.remove(object);
    }

    @Override
    public User getById(@Nonnull Long id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Nonnull
    @Override
    public Collection<User> getAll() {
        return users;
    }

    public void removeAll() {
        users.clear();
    }


}
