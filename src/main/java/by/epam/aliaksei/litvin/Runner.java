package by.epam.aliaksei.litvin;

import by.epam.aliaksei.litvin.domain.User;
import by.epam.aliaksei.litvin.service.impl.AuditoriumServiceImpl;
import by.epam.aliaksei.litvin.service.impl.UserServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

public class Runner {

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
        AuditoriumServiceImpl auditoriumServiceImpl = ctx.getBean("auditoriumServiceImpl", AuditoriumServiceImpl.class);

        System.out.println(auditoriumServiceImpl);
    }
}
