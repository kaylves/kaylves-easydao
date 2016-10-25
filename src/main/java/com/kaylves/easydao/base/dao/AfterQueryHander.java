package com.kaylves.easydao.base.dao;

import java.util.List;

/**
 * Copyright(c) 2013 hofort. All Rights Reserved.
 * Compiler: JDK1.6.0_23
 * @author Kaylves
 * @create_date 2014-6-18 下午04:19:44
 * @version 1.0
 * @update_user Kaylves
 * @update_date 2014-6-18 下午04:19:44
 * @description 此处写类的描述
 */
public interface AfterQueryHander {
	
	/**
	 * 
	 * @author Kaylves
	 * @time 2014-6-18 下午04:27:15
	 * @param <T>
	 * @param result	查询后的结果集
	 * @param sql		查询时的SQL OR HQL
	 * @param paraObj	查询时的条件
	 * @return List<T>
	 * @description
	 * @version 1.0
	 */
	<T> List<T> doAfterQueryHander(List<T> result,String sql,Object paraObj);

}
