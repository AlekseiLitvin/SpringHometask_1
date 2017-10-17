package by.epam.aliaksei.litvin.service.impl;

import by.epam.aliaksei.litvin.daos.UserDao;
import by.epam.aliaksei.litvin.domain.User;
import by.epam.aliaksei.litvin.service.UserService;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.sql.Date;
import java.time.Instant;
import java.time.ZoneId;
import java.util.*;

public class UserServiceImpl implements UserService {

    private UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Nullable
    @Override
    public User getUserByEmail(@Nonnull String email) {
        return userDao.getUserByEmail(email);
    }

    @Override
    public User save(@Nonnull User user) {
        userDao.save(user);
        return user;
    }

    @Override
    public void remove(@Nonnull User user) {
        userDao.remove(user);
    }

    @Override
    public User getById(@Nonnull String id) {
        return userDao.getById(id);
    }

    @Nonnull
    @Override
    public Collection<User> getAll() {
        return userDao.getAll();
    }

    public void removeAll() {
        userDao.removeAll();
    }
    
}
