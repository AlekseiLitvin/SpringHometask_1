package by.epam.aliaksei.litvin.services;

import by.epam.aliaksei.litvin.domain.Auditorium;
import by.epam.aliaksei.litvin.service.impl.AuditoriumServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/tests.xml")
public class AuditoriumServiceImplTest {

    private static final String BIG_AUDITORUM_NAME = "Big auditorium";

    @Autowired
    private AuditoriumServiceImpl auditoriumService;

    @Test
    public void testInit() {
        assertEquals(auditoriumService.getAll().size(), 3);
    }

    @Test
    public void testGetByName() {
        Auditorium bigAuditorium = auditoriumService.getByName(BIG_AUDITORUM_NAME);
        assertEquals(bigAuditorium.getName(), BIG_AUDITORUM_NAME);
    }

}
