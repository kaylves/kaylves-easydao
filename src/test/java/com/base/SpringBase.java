package com.base;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ycii.core.base.dao.impl.JEasyDaoImpl;


@RunWith(SpringJUnit4ClassRunner.class)
// ApplicationContext will be loaded from "/applicationContext.xml" and "/applicationContext-test.xml"
// in the root of the classpath
//classpath:spring.xml,classpath:spring-hibernate.xml,classpath:spring-ehcache.xml
@ContextConfiguration(locations={"classpath:/spring/spring-*.xml"})
public class SpringBase {
    
    @Autowired
    private SessionFactory sessionFactory;
    
	@Autowired
	private JEasyDaoImpl baseDao;
	


	@Test
	public void run(){
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("orderType", 	"-1");
			map.put("updateType", 	"1");
			baseDao.getSqlStatementById("updateCodeWithDynamic",		map);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test()
	{
	    System.out.println(sessionFactory);

	}
	
}
