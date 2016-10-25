package com.kaylves.easydao.base.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.transform.ResultTransformer;

import com.kaylves.easydao.base.pojo.Pagination;


/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  kaylves
 * @version  [版本号, 2015年4月17日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface BaseDao {
	
	/**
	 * 保存对象
	 * @author Kaylves
	 * @time 2014-6-15 下午08:18:59
	 * @param object
	 * void
	 * @description
	 * @version 1.0
	 */
	void saveObject(Object object);
	
	/**
	 * 保存集合
	 * @author Kaylves
	 * @time 2014-6-15 下午08:19:05
	 * @param collection
	 * void
	 * @description
	 * @version 1.0
	 */
	void saveAllOrUpdateAll(Collection<?> collection);
	
	/**
	 * 
	 * @author Kaylves
	 * @time 2014-6-17 下午02:19:38
	 * @param hql
	 * @param param
	 * void
	 * @description
	 * @version 1.0
	 */
	void executeHql(String hql,Object... param);
	
	/**
	 * 
	 * @author Kaylves
	 * @time 2014-6-17 下午04:00:13
	 * @param sql
	 * @param param
	 * void
	 * @description
	 * @version 1.0
	 */
	void executeSql(String sql,Object... param);
	
	/**
	 * 
	 * @author Kaylves
	 * @time 2014-6-15 下午08:19:09
	 * @param object
	 * void
	 * @description
	 * @version 1.0
	 */
	void updateObject(Object object);
	/**
	 * 
	 * @author Kaylves
	 * @time 2014-6-15 下午08:19:12
	 * @param object
	 * void
	 * @description
	 * @version 1.0
	 */
	void removeObject(Object object);
	/**
	 * 删除对象
	 * @author Kaylves
	 * @time 2014-6-15 下午08:19:17
	 * @param clzss
	 * @param id
	 * void
	 * @description
	 * @version 1.0
	 */
	void removeObjectById(Class<?> clzss, String id);
	
	/**
	 * 删除集合
	 * @author Kaylves
	 * @time 2014-6-15 下午08:19:22
	 * @param collection
	 * void
	 * @description
	 * @version 1.0
	 */
	void removeAll(Collection<?> collection);
	
	/**
	 * 
	 * @author Kaylves
	 * @time 2014-6-17 下午02:25:35
	 * @param <T>
	 * @param entityClass
	 * @param id
	 * @return
	 * T
	 * @description
	 * @version 1.0
	 */
	<T> T get(Class<T> entityClass, Serializable id);
	/**
	 * 
	 * @author Kaylves
	 * @time 2014-6-17 下午03:50:40
	 * @param <T>
	 * @param hql
	 * @param param
	 * @return
	 * List<T>
	 * @description
	 * @version 1.0
	 */
	<T> List<T> findListByHql(final String hql,final Object... param);
	
	<T> List<T> findListByHql(final String hql,boolean cacheAble,final Object... param);
	
	/**
	 * 
	 * @param hql
	 * @param param
	 */
	Object getUniqueResult(String hql,Object... param);
	
	/**
	 * 
	 * @author Kaylves
	 * @time 2014-6-17 上午08:55:00
	 * @param <T>
	 * @param sqlId
	 * @param paramMap
	 * @return
	 * List<T>
	 * @description
	 * @version 1.0
	 */
	<T> List<T> findListBySqlId(String sqlId, Map<String,Object> paramMap);
	
	/**
	 * 重载findListBySqlId方法，添加了回调
	 * @author Kaylves
	 * @time 2014-6-18 下午04:33:36
	 * @param <T>
	 * @param sqlId
	 * @param paramMap
	 * @param afterQueryHander
	 * @return
	 * List<T>
	 * @description
	 * @version 1.0
	 */
	<T> List<T> findListBySqlId(String sqlId, Map<String,Object> paramMap,AfterQueryHander afterQueryHander);
	
	/**
	 * 
	 * @author Kaylves
	 * @time 2014-6-23 上午09:28:50
	 * @param <T>
	 * @param sqlId				sqlId
	 * @param paraMap			参数
	 * @param afterQueryHander	查询后对结果集的预处理
	 * @param cacheAble			是否开启缓存
	 * @return
	 * List<T>
	 * @description
	 * @version 1.0
	 */
	<T> List<T> findListBySqlId(String sqlId, Map<String,Object> paraMap,AfterQueryHander afterQueryHander,boolean cacheAble);
	
	
	/**
	 * 
	 * @author Kaylves
	 * @time 2014-6-15 下午08:20:06
	 * @param sql
	 * @param paramPagination
	 * @return
	 * Pagination
	 * @description
	 * @version 1.0
	 */
	Pagination findPageBySql(String sql, Pagination paramPagination);
	
	/**
	 * 
	 * @author Kaylves
	 * @time 2014-6-17 上午08:55:20
	 * @param <T>
	 * @param sql
	 * @return
	 * List<T>
	 * @description
	 * @version 1.0
	 */
	<T> List<T> findListBySql(String sql);
	
	/**
	 * 
	 * @author Kaylves
	 * @time 2014-6-17 下午04:09:51
	 * @param <T>
	 * @param sql
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * List<T>
	 * @description
	 * @version 1.0
	 */
	<T> List<T> findListBySql(String sql,int pageNo,int pageSize);
	
	/**
	 * 
	 * @author Kaylves
	 * @time 2014-6-17 下午04:09:55
	 * @param <T>
	 * @param sql
	 * @param transMap
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * List<T>
	 * @description
	 * @version 1.0
	 */
	<T> List<T> findListBySql(String sql,boolean transMap,int pageNo,int pageSize);
	
	
	/**
	 * 
	 * @author Kaylves
	 * @time 2014-6-15 下午08:20:25
	 * @param hql
	 * @param pagination
	 * @return
	 * Pagination
	 * @description
	 * @version 1.0
	 */
	Pagination findPageByHql(String hql, Pagination pagination);
	
	/**
	 * 
	 * @author Kaylves
	 * @time 2014-6-17 上午08:55:37
	 * @param <T>
	 * @param hqlId
	 * @param paramMap
	 * @return
	 * List<T>
	 * @description
	 * @version 1.0
	 */
	<T> List<T> findListByHqlId(String hqlId, Map<String,Object> paramMap);
	
	/**
	 * 重载了findListByHqlId方法，添加了回调
	 * @author Kaylves
	 * @time 2014-6-18 下午04:34:50
	 * @param <T>
	 * @param hqlId
	 * @param paramMap
	 * @param afterQueryHander
	 * @return
	 * List<T>
	 * @description
	 * @version 1.0
	 */
	<T> List<T> findListByHqlId(String hqlId, Map<String,Object> paramMap,AfterQueryHander afterQueryHander);
	
	/**
	 * 
	 * @author Kaylves
	 * @time 2014-6-23 上午09:26:39
	 * @param <T>
	 * @param hqlId
	 * @param paraMap
	 * @param afterQueryHander
	 * @param cacheAble				是否开启缓存
	 * @return
	 * List<T>
	 * @description
	 * @version 1.0
	 */
	<T> List<T> findListByHqlId(String hqlId, Map<String,Object> paraMap,AfterQueryHander afterQueryHander,boolean cacheAble);
	
	/**
	 * 
	 * @author Kaylves
	 * @time 2014-6-17 上午08:56:06
	 * @param <T>
	 * @param hql
	 * @return
	 * List<T>
	 * @description
	 * @version 1.0
	 */
	<T> List<T> findListByHql(String hql);
	
	/**
	 * 
	 * @author Kaylves
	 * @time 2014-6-15 下午08:48:10
	 * @param sqlId
	 * @param paramMap
	 * @param pagination
	 * @return
	 * Pagination
	 * @description
	 * @version 1.0
	 */
	Pagination findPageBySqlId(String sqlId, Map<String,Object> paramMap,Pagination pagination);
	
	/**
	 * 重载findPageBySqlId方法，多了查询后的回调
	 * @author Kaylves
	 * @time 2014-6-18 下午04:24:03
	 * @param sqlId
	 * @param paraMap
	 * @param pagination
	 * @param afterHander
	 * @return
	 * Pagination
	 * @description
	 * @version 1.0
	 */
	Pagination findPageBySqlId(String sqlId, Map<String,Object> paraMap,Pagination pagination,AfterQueryHander afterHander);
	
	/**
	 * 
	 * @author Kaylves
	 * @time 2014-6-15 下午08:48:15
	 * @param hqlId
	 * @param paramMap
	 * @param pagination
	 * @return
	 * Pagination
	 * @description
	 * @version 1.0
	 */
	Pagination findPageByHqlId(String hqlId, Map<String,Object> paramMap,Pagination pagination);
	
	/**
	 * 重载findPageByHqlId方法，多了查询后的回调
	 * @author Kaylves
	 * @time 2014-6-18 下午04:26:44
	 * @param hqlId
	 * @param paramMap
	 * @param pagination
	 * @return
	 * Pagination
	 * @description
	 * @version 1.0
	 */
	Pagination findPageByHqlId(String hqlId, Map<String,Object> paramMap,Pagination pagination,AfterQueryHander afterHander);
	
	/**
	 * 
	 * @author Kaylves
	 * @time 2014-6-17 上午08:56:30
	 * @param <T>
	 * @param clazz
	 * @return
	 * List<T>
	 * @description
	 * @version 1.0
	 */
	<T> List<T> getAllObject(Class<T> clazz);
	
	/**
	 * 
	 * @author Kaylves
	 * @time 2014-6-15 下午08:48:27
	 * @param paramClass
	 * @param str
	 * @param object
	 * @return
	 * Object
	 * @description
	 * @version 1.0
	 */
	Object getObjectByProperty(Class<?> paramClass, String str,Object object);
	
	/**
	 * 
	 * @author Kaylves
	 * @time 2014-6-15 下午08:48:33
	 * @param clzss
	 * @param str
	 * @return
	 * Object
	 * @description
	 * @version 1.0
	 */
	Object getObjectById(Class<?> clzss, String str);
	
	/**
	 * 
	 * @author Kaylves
	 * @time 2014-6-15 下午08:48:42
	 * @param str
	 * @param paramMap
	 * @return
	 * int
	 * @description
	 * @version 1.0
	 */
	int executeUpdateBySqlId(String str, Map<String,Object> paramMap);
	/**
	 * 
	 * @author Kaylves
	 * @time 2014-6-17 上午08:57:18
	 * @param str
	 * @param paramMap
	 * @return
	 * int
	 * @description
	 * @version 1.0
	 */
	int executeUpdateByHqlId(String str, Map<String,Object> paramMap);
	/**
	 * 
	 * @author Kaylves
	 * @time 2014-6-17 上午08:57:15
	 * @param object
	 * void
	 * @description
	 * @version 1.0
	 */
	void merge(Object object);
	
	/**
	 * 
	 * @author Kaylves
	 * @time 2014-6-17 上午08:57:12
	 * void
	 * @description
	 * @version 1.0
	 */
	void flush();
	/**
	 * 
	 * @author Kaylves
	 * @time 2014-6-17 上午08:57:08
	 * @param object
	 * void
	 * @description
	 * @version 1.0
	 */
	void saveOrUpdateObject(Object object);
	/**
	 * 
	 * @author Kaylves
	 * @time 2014-6-17 上午08:57:04
	 * @param object
	 * @param paramLockMode
	 * void
	 * @description
	 * @version 1.0
	 */
	void refresh(Object object, LockMode paramLockMode);
	
	/**
	 * 
	 * @author Kaylves
	 * @time 2014-6-17 上午08:56:59
	 * @return
	 * Session
	 * @description
	 * @version 1.0
	 */
	Session getSession();
	/**
	 * 
	 * @author Kaylves
	 * @time 2014-6-17 上午08:56:54
	 * @param clz
	 * @param paramMap
	 * @return
	 * Object
	 * @description
	 * @version 1.0
	 */
	Object findObjectByFieldsMap(Class<?> clz, Map<String,Object> paramMap);
	/**
	 * 
	 * @author Kaylves
	 * @time 2014-6-17 上午08:56:50
	 * @param <T>
	 * @param str
	 * @param paramArrayOfObject
	 * @return
	 * List<T>
	 * @description
	 * @version 1.0
	 */
	<T> List<T> callProcedure(String str, Object[] paramArrayOfObject);
	/**
	 * 
	 * @author Kaylves
	 * @time 2014-6-17 上午08:57:30
	 * @param str
	 * @param inPara
	 * @param outPara
	 * @return
	 * Map<Integer,Object>
	 * @description
	 * @version 1.0
	 */
	Map<Integer, Object> callProcedure(String str,Map<Integer, Object> inPara, Map<Integer, Integer> outPara);
	
	/**
     * <一句话功能简述>查询返回List
     * <功能详细描述>
     * @author  kaylves
     * @time  2015年4月17日 下午3:31:13
     * @param sqlId
     * @param paraMap
     * @param transformer
     * @return [参数说明]
     * 
     * @return List<T> [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    <T> List<T>    findListBySqlId(String sqlId,Map<String,Object> paraMap,ResultTransformer transformer);
    
    /**
     * <一句话功能简述>查询返回List
     * <功能详细描述>
     * @author  kaylves
     * @time  2015年4月17日 下午3:31:06
     * @param sqlId
     * @param paraMap
     * @param transformer
     * @param afterHander
     * @return [参数说明]
     * 
     * @return List<T> [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    <T> List<T>    findListBySqlId(String sqlId,Map<String,Object> paraMap,ResultTransformer transformer,AfterQueryHander afterHander);

}