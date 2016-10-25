package com.kaylves.easydao.test;

import org.junit.Test;

import com.kaylves.easydao.base.dao.impl.JEasyDaoImpl;


public class BaseDaoImplTest {
	
	@Test
	public void baseDaoTest(){
		JEasyDaoImpl base = new JEasyDaoImpl();
		String s = base.getCountSql(
				"select v.sell_cs_name, sum(v.sell_amounts) sell_amounts,sum(v.quantity) quantity\n" +
						"  from v_retail_sell_report v\n" + 
						" group by v.sell_cs_name", true);
		System.out.println(s);
	}
}
