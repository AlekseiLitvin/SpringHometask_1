package by.epam.aliaksei.litvin.daos.impl;

import by.epam.aliaksei.litvin.daos.UserDao;
import by.epam.aliaksei.litvin.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.sql.Date;
import java.util.*;

public class UserDaoImpl implements UserDao {

    private JdbcTemplate jdbcTemplate;

    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void init() {
        jdbcTemplate.update("CREATE TABLE users (\n" +
                "  id         VARCHAR(50) PRIMARY KEY,\n" +
                "  firstName VARCHAR(30),\n" +
                "  lastName VARCHAR(30),\n" +
                "  email  VARCHAR(50),\n" +
                "  birthday  DATE\n" +
                ")");
    }

    @Nullable
    @Override
    public User getUserByEmail(@Nonnull String email) {
        return getByField("email", email);
    }

    @Override
    public User save(@Nonnull User user) {
        jdbcTemplate.update("INSERT INTO users(id, firstName, lastName, email, birthday) VALUES (?, ?, ?, ?, ?)",
                UUID.randomUUID().toString(), user.getFirstName(), user.getLastName(), user.getEmail(), Date.valueOf(user.getBirthday()));
        return user;
    }

    @Override
    public void remove(@Nonnull User user) {
        jdbcTemplate.update("DELETE FROM users WHERE id = ?", user.getId());
    }

    @Override
    public User getById(@Nonnull String id) {
        return getByField("id", id);
    }

    @Nonnull
    @Override
    public Collection<User> getAll() {
        List<User> result = new ArrayList<>();
        List<Map<String, Object>> users = jdbcTemplate.queryForList("SELECT * FROM users");
        users.forEach(it -> {
            User user = new User();
            user.setId((String) it.get("ID"));
            user.setFirstName((String) it.get("firstName"));
            user.setLastName((String) it.get("lastName"));
            user.setEmail((String) it.get("email"));
            Date date = (Date) it.get("birthday");
            user.setBirthday(date.toLocalDate());
            result.add(user);
        });
        return result;
    }

    public void removeAll() {
        jdbcTemplate.update("DELETE FROM users");
    }

    private User getByField(String fieldName, String fieldValue) {
        User user = null;
        List<Map<String, Object>> users = jdbcTemplate.queryForList("SELECT * FROM users WHERE " + fieldName + " = ?", fieldValue);
        if (!users.isEmpty()) {
            user = new User();
            Map<String, Object> userAttributes = users.get(0);
            user.setId((String) userAttributes.get("ID"));
            user.setFirstName((String) userAttributes.get("firstName"));
            user.setLastName((String) userAttributes.get("lastName"));
            user.setEmail((String) userAttributes.get("email"));
            Date date = (Date) userAttributes.get("birthday");
            user.setBirthday(date.toLocalDate());
        }
        return user;
    }
}
