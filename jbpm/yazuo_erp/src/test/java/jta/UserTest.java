package jta;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zdp.service.UserServiceJTA;

public class UserTest {
	@Test
	public void testSave() {
		ApplicationContext cxt = new ClassPathXmlApplicationContext("ApplicationContext2.xml");
		UserServiceJTA us = (UserServiceJTA) cxt.getBean("userServiceJTA");
		us.saveUser("1", "fffffff");
	}
}
