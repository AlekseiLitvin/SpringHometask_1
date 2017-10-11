package by.epam.aliaksei.litvin;

import by.epam.aliaksei.litvin.config.AppConfig;
import by.epam.aliaksei.litvin.domain.User;
import by.epam.aliaksei.litvin.service.UserService;
import by.epam.aliaksei.litvin.service.impl.AuditoriumServiceImpl;
import by.epam.aliaksei.litvin.service.impl.UserServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.*;

public class Runner {

    public static void main(String[] args) {

        AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        AuditoriumServiceImpl bean = ctx.getBean(AuditoriumServiceImpl.class);
        System.out.println(bean == null);
    }
}
