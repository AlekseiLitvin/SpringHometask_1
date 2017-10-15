package by.epam.aliaksei.litvin.services;

import by.epam.aliaksei.litvin.config.TestAppConfig;
import by.epam.aliaksei.litvin.domain.User;
import by.epam.aliaksei.litvin.service.impl.UserServiceImpl;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestAppConfig.class, loader = AnnotationConfigContextLoader.class)
public class UserServiceImplTest {

    @Autowired
    private UserServiceImpl userService;

    private static final String EMAIL1 = "test1@test.com";
    private static final String EMAIL2 = "test2@test.com";
    private static final String EMAIL3 = "test3@test.com";

    private User user1;
    private User user2;
    private User user3;

    @Before
    public void setup() {

        user1 = new User();
        user2 = new User();
        user3 = new User();

        user1.setBirthday(LocalDate.now());
        user2.setBirthday(LocalDate.now());
        user3.setBirthday(LocalDate.now());

        user1.setEmail(EMAIL1);
        user2.setEmail(EMAIL2);
        user3.setEmail(EMAIL3);

        userService.save(user1);
        userService.save(user2);
        userService.save(user3);
    }

    @After
    public void tearDown() {
        userService.removeAll();
    }


    @Test
    public void testSaveUser_andGetAllUsers() {
        assertEquals(userService.getAll().size(), 3);
    }

    @Test
    public void testRemoveUser() {
        User userByEmail = userService.getUserByEmail(EMAIL1);
        userService.remove(userByEmail);
        assertEquals(userService.getAll().size(), 2);
    }


    @Test
    public void testGetUserByParameter() {
        User userByEmail = userService.getUserByEmail(EMAIL1);
        String id = userByEmail.getId();
        User userById = userService.getById(id);

        assertEquals(userById.getId(), id);
        assertEquals(userByEmail.getEmail(), EMAIL1);
    }

}
