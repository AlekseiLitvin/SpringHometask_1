package by.epam.aliaksei.litvin.daos;

import by.epam.aliaksei.litvin.domain.User;

public interface UserDao extends AbstractDomainObjectDao<User> {
    User getUserByEmail(String email);
}
