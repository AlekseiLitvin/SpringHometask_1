package by.epam.aliaksei.litvin.services;

import by.epam.aliaksei.litvin.config.TestAppConfig;
import by.epam.aliaksei.litvin.domain.User;
import by.epam.aliaksei.litvin.service.impl.UserServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestAppConfig.class, loader = AnnotationConfigContextLoader.class)
public class UserServiceImplTest {

    @Autowired
    private UserServiceImpl userService;

    private static final String EMAIL = "test@test.com";

    private User user1;
    private User user2;
    private User user3;

    @Before
    public void setup() {
        user1 = new User();
        user2 = new User();
        user3 = new User();

        user1.setEmail(EMAIL);

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
        userService.remove(user1);
        assertEquals(userService.getAll().size(), 2);
    }

    @Test
    public void testGetUserById() {
        Long id = 0L;
        User user = userService.getById(id);
        assertEquals(user.getId(), id);
    }

    @Test
    public void testGetUserByEmail() {
        User userByEmail = userService.getUserByEmail(EMAIL);
        assertEquals(userByEmail.getEmail(), EMAIL);
    }

}
