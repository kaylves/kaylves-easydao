package com.kaylves.easydao.test;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
// ApplicationContext will be loaded from "/applicationContext.xml" and "/applicationContext-test.xml"
// in the root of the classpath
//classpath:spring.xml,classpath:spring-hibernate.xml,classpath:spring-ehcache.xml
@ContextConfiguration(locations={"classpath:/spring/spring-*.xml"})
public class BaseBean {
    
    @Autowired
    private SessionFactory sessionFactory;

	@Test
	public void test()
	{
	    System.out.println(sessionFactory);

	}
	
}
