package by.epam.aliaksei.litvin;

import by.epam.aliaksei.litvin.domain.User;
import by.epam.aliaksei.litvin.service.impl.UserServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Runner {

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
        UserServiceImpl userServiceImpl = ctx.getBean("userServiceImpl", UserServiceImpl.class);
        userServiceImpl.save(new User());
        userServiceImpl.save(new User());

        System.out.println(userServiceImpl.getAll());
    }
}
