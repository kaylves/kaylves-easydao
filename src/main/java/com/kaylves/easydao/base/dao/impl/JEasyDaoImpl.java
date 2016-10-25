package com.kaylves.easydao.base.dao.impl;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.ResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.kaylves.easydao.base.dao.AfterQueryHander;
import com.kaylves.easydao.base.dao.JEasyDao;
import com.kaylves.easydao.base.entity.BaseEntity;
import com.kaylves.easydao.base.pojo.Pagination;
import com.kaylves.easydao.exception.BaseDaoException;
import com.kaylves.easydao.orm.mybatis.MybatisSqlSessionFactoryBean;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  kaylves
 * @version  [版本号, 2015年4月17日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@SuppressWarnings({"unchecked","rawtypes"})
@Repository
public class JEasyDaoImpl implements JEasyDao{
	

	/**
	 * 使用slf4j Log方便子类继承
	 */
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * Mybatis SqlSessionFactoryBean
	 */
	@Autowired
	private MybatisSqlSessionFactoryBean sqlSessionFactoryBean;
	
	/**
	 * HibernateTemplate
	 */
	@Autowired
	private HibernateTemplate hibernateTemplate;

	public JEasyDaoImpl() {
		
	}
	
	public HibernateTemplate getHibernateTemplate() {
		return this.hibernateTemplate;
	}

	public Session getSession() {
		return this.hibernateTemplate.getSessionFactory().getCurrentSession();
	}
	
	public void executeHql(String hql,Object... param) {
		Query query = getSession().createQuery(hql);
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				query.setParameter(i, param[i]);
			}
		}
		query.executeUpdate();
	}
	
	public void executeSql(String sql,Object... param) {
		Query query = getSession().createSQLQuery(sql);
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				query.setParameter(i, param[i]);
			}
		}
		query.executeUpdate();
	}
	
	public Object getUniqueResult(String hql,Object... params){
		Query uniqueResult = getSession().createQuery(hql);
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				uniqueResult.setParameter(i, params[i]);
			}
		}
		return uniqueResult.uniqueResult();
	}
	
	
	public <T> List<T> findListByHql(final String hql,final Object... param) {
		List list = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
			throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				if (param != null && param.length > 0) {
					for (int i = 0; i < param.length; i++) {
						query.setParameter(i, param[i]);
					}
				}
				return query.list();
			}
		});
		return list;
	}
	
	public <T> List<T> findListByHql(final String hql,final boolean cacheAble,final Object... param){
		List list = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
			throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				if(cacheAble){
					query.setCacheable(true);
				}
				if (param != null && param.length > 0) {
					for (int i = 0; i < param.length; i++) {
						query.setParameter(i, param[i]);
					}
				}
				return query.list();
			}
		});
		return list;
		
	}
	
	public <T> T get(Class<T> entityClass, Serializable id){
		return this.hibernateTemplate.get(entityClass, id);
	}
	
	
	public <T> List<T> findListByHqlId(String hqlId, Map<String,Object> paraMap) {
		return findListByHqlId(hqlId, paraMap,null);
	}
	
	public <T> List<T> findListByHqlId(String hqlId, Map<String,Object> paraMap,AfterQueryHander afterQueryHander) {
		return findListByHqlId(hqlId, paraMap, afterQueryHander,false);
	}
	
	public <T> List<T> findListByHqlId(String hqlId, Map<String,Object> paraMap,AfterQueryHander afterQueryHander,boolean cacheAble) {
		try {
			String hsql = getSqlStatementById(hqlId, paraMap);
			Query query = getSession().createQuery(hsql);
			
			if(cacheAble){
				query.setCacheable(true);
			}
			
			setQueryParameters(query, paraMap);
			
			if(afterQueryHander!=null){
				return afterQueryHander.doAfterQueryHander(query.list(), hsql, paraMap);
			}else{
				return query.list();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new BaseDaoException(e.getCause().getMessage());
		}
	}

	public <T> List<T> findListBySqlId(String sqlId, Map<String,Object> paraMap) {
		List<T> list = null;
		try {
			String hql = getSqlStatementById(sqlId, paraMap);
			Query query = getSession().createSQLQuery(hql);
			setQueryParameters(query, paraMap);
			list = query.list();
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new BaseDaoException(e.getCause().getMessage());
		}
		return list;
	}
	
	public <T> List<T> findListBySqlId(String sqlId, Map<String,Object> paraMap,AfterQueryHander afterQueryHander) {
		return findListBySqlId(sqlId, paraMap, afterQueryHander,false);
	}

	public <T> List<T> findListBySqlId(String sqlId, Map<String,Object> paraMap,AfterQueryHander afterQueryHander,boolean cacheAble) {
		try {
			String sql = getSqlStatementById(sqlId, paraMap);
			Query query = getSession().createSQLQuery(sql);
			if(cacheAble){
				query.setCacheable(true);
			}
			setQueryParameters(query, paraMap);
			if(afterQueryHander!=null){
				return afterQueryHander.doAfterQueryHander(query.list(), sql, paraMap);
			}else{
				return query.list();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new BaseDaoException(e.getCause().getMessage());
		}
	}

	public Pagination findPageByHqlId(String hqlId, Map<String,Object> paraMap,Pagination pagination,AfterQueryHander afterHander) {
		String hsql = getSqlStatementById(hqlId, paraMap);
		String countSql = getCountSql(hsql,true);
		try {
			int count = 0;
			if (countSql != null) {
				Query countQuery = getSession().createQuery(countSql);
				setQueryParameters(countQuery, paraMap);
				count = Integer.parseInt(countQuery.uniqueResult().toString());
				pagination.setTotalCount(count);
			}
			if ((hsql != null) && (count != 0)) {
				StringBuffer buffHSQL = new StringBuffer(hsql);
				Query query = getSession().createQuery(buffHSQL.toString());
				setQueryParameters(query, paraMap);
				query.setFirstResult((pagination.getPageNumber()-1)*pagination.getPageSize());
				query.setMaxResults(pagination.getPageSize());
				if(afterHander!=null){
					pagination.setResultList(afterHander.doAfterQueryHander(query.list(), hsql, paraMap));
				}else{
					pagination.setResultList(query.list());
				}
			} else {
				if (count == 0){
					pagination.setResultList(Collections.emptyList());
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new BaseDaoException(e.getCause().getMessage());
		}
		return pagination;
	}
	
	public Pagination findPageByHqlId(String hqlId, Map<String,Object> paraMap,Pagination pagination) {
		return findPageByHqlId(hqlId, paraMap, pagination, null);
	}

	public Pagination findPageBySqlId(String sqlId, Map<String,Object> paraMap,Pagination pagination) {
		return findPageBySqlId(sqlId, paraMap, pagination,null);
	}
	
	public Pagination findPageBySqlId(String sqlId, Map<String,Object> paraMap,Pagination pagination,AfterQueryHander afterHander) {
		String hsql = getSqlStatementById(sqlId, paraMap);
		String countSql = getCountSql(hsql,false);
		try {
			int count = 0;
			if (countSql != null) {
				Query countQuery = getSession().createSQLQuery(countSql);
				setQueryParameters(countQuery, paraMap);
				count = Integer.parseInt(countQuery.uniqueResult().toString());
				pagination.setTotalCount(count);
			}
			if ((hsql != null) && (count != 0)) {
				StringBuffer buffHSQL = new StringBuffer(hsql);
				Query query = getSession().createSQLQuery(buffHSQL.toString());
				setQueryParameters(query, paraMap);
				query.setFirstResult((pagination.getPageNumber()-1)*pagination.getPageSize());
				query.setMaxResults(pagination.getPageSize());
				if(afterHander!=null){
					pagination.setResultList(afterHander.doAfterQueryHander(query.list(), hsql, paraMap));
				}else{
					pagination.setResultList(query.list());
				}
			} else {
				if (count == 0){
					pagination.setResultList(Collections.emptyList());
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new BaseDaoException(e.getCause().getMessage());
		}
		return pagination;
	}

	public void flush() {
		getHibernateTemplate().flush();
	}

	public Object getObjectById(Class<?> entity, String id) {
		return getHibernateTemplate().get(entity, id);
	}

	public Object getObjectByProperty(Class<?> entity, String name, Object value) {
		Criteria criteria = getSession().createCriteria(entity);
		criteria.add(Restrictions.eq(name, value));
		criteria.setMaxResults(1);
		return criteria.uniqueResult();
	}

	public void merge(Object object) {
		getHibernateTemplate().merge(object);
	}

	public void removeAll(Collection<?> collection) {
		getHibernateTemplate().deleteAll(collection);
	}

	public void removeObject(Object object) {
		getHibernateTemplate().delete(object);
	}

	public void removeObjectById(Class<?> entity, String id) {
		getHibernateTemplate().delete(getObjectById(entity, id));
	}

	public void refresh(Object obj, LockMode mode) {
		getHibernateTemplate().refresh(obj, mode);
	}

	public void saveAllOrUpdateAll(Collection<?> collection) {
		getHibernateTemplate().saveOrUpdateAll(collection);
	}

	public void saveBaseModelObject(BaseEntity entity) {
		getHibernateTemplate().saveOrUpdate(entity);
	}

	public void saveOrUpdateBaseModelObject(BaseEntity entity) {
		if (StringUtils.isNotEmpty(entity.getId()))
			getHibernateTemplate().update(entity);
		else
			getHibernateTemplate().save(entity);
	}

	public void saveOrUpdateObject(Object object) {
		getHibernateTemplate().saveOrUpdate(object);
	}

	public void saveObject(Object object) {
		if ((object instanceof BaseEntity)) {
			getHibernateTemplate().save(object);
		} else
			getHibernateTemplate().saveOrUpdate(object);
	}

	public void updateBaseModelObject(BaseEntity entity) {
		getHibernateTemplate().update(entity);
	}

	public void updateObject(Object object) {
		getHibernateTemplate().update(object);
	}

	protected void setQueryParameters(Query query, Map<String,Object> paraMap) {
		if ((query != null) && (paraMap != null) && (!paraMap.isEmpty())) {
			List<?> namedParms = Arrays.asList(query.getNamedParameters());
			Iterator iter = paraMap.keySet().iterator();
			while (iter.hasNext()) {
				String paraName = (String) iter.next();
				if (namedParms.contains(paraName)) {
					Object value = paraMap.get(paraName);
					if ((value instanceof List)){
						query.setParameterList(paraName, (List) value);
					}else{
						query.setParameter(paraName, value);
					}
				}
			}
		}
	}

	@SuppressWarnings("unused")
	private String convertToCountSqlStatement(String selectSQL) {
		if (!StringUtils.isEmpty(selectSQL)) {
			String patternStr = "\\sfrom\\s";
			Pattern pattern = Pattern.compile(patternStr);
			String[] splitArr = pattern.split(selectSQL.toLowerCase());
			if ((splitArr != null) && (splitArr.length > 0)) {
				int indexOfFrom = splitArr[0].length();
				int indexOfOderBy = selectSQL.toLowerCase().indexOf(" order ");
				if (indexOfOderBy >= 0) {
					return convertCountSql("select count(*) "+ selectSQL.substring(indexOfFrom, indexOfOderBy));
				}
				return convertCountSql("select count(*) "+ selectSQL.substring(indexOfFrom));
			}
		}
		return null;
	}

	private String convertCountSql(String sql) {
		String tempSql = sql;
		int leftBrackets = tempSql.length() - tempSql.replace("(", "").length();
		int rightBrackets = tempSql.length()
				- tempSql.replace(")", "").length();
		if (leftBrackets > rightBrackets) {
			String temp = "";
			for (int i = 0; i < leftBrackets - rightBrackets; i++) {
				temp = temp + ")";
			}
			sql = sql + temp;
		}
		return sql;
	}

	public String getSqlStatementById(String sqlId, Object object) {
		if (this.sqlSessionFactoryBean == null) {
			this.logger.error(" sqlSessionFactory 未设置!");
			throw new RuntimeException(" sqlSessionFactory 未设置!");
		}
		Configuration configuration=null;
		String sql=null;
		try {
			configuration = sqlSessionFactoryBean.getObject().getConfiguration();
			final MappedStatement ms = configuration.getMappedStatement(sqlId);
			final BoundSql boundSql  = ms.getBoundSql(object);
			sql = boundSql.getSql();
		} catch (Exception e) {
			logger.error("Sql配置文件中配置出错",e);
			sql=null;
			throw new BaseDaoException("Sql配置出错, ID为" + sqlId+ "的hql 语句");
		}
		logger.info("sql:"+sql);
		return sql;
	}

	public int executeUpdateBySqlId(String sqlid, Map<String,Object> paraMap) {
		String hsql = getSqlStatementById(sqlid, paraMap);
		if (hsql != null) {
			Query query = getSession().createSQLQuery(hsql);
			setQueryParameters(query, paraMap);
			return query.executeUpdate();
		}
		return 0;
	}

	public int executeUpdateByHqlId(String hqlid, Map<String,Object> paraMap) {
		String hsql = getSqlStatementById(hqlid, paraMap);
		Query query = getSession().createQuery(hsql);
		setQueryParameters(query, paraMap);
		return query.executeUpdate();
	}

	public Pagination findPageBySql(String sql, Pagination pagination) {
		String countSql = getCountSql(sql,false);
		if (sql != null) {
			StringBuffer buffHSQL = new StringBuffer(sql);
			Query query = getSession().createSQLQuery(buffHSQL.toString());
			query.setFirstResult((pagination.getPageNumber()-1)*pagination.getPageSize());
			query.setMaxResults(pagination.getPageSize());
			List<?> restList = query.list();
			pagination.setResultList(restList);
			if (countSql != null) {
				Query countQuery = getSession().createSQLQuery(countSql);
				int count = Integer.parseInt(countQuery.uniqueResult().toString());
				pagination.setTotalCount(count);
			}
		}
		return pagination;
	}
	
	public <T>  List<T> findListBySql(String sql,boolean transMap,int pageNo,int pageSize) {
		if (StringUtils.isEmpty(sql)) {
			throw new BaseDaoException("sql不能为空！");
		}
		StringBuffer buffHSQL = new StringBuffer(sql);
		Query query = getSession().createSQLQuery(buffHSQL.toString());
		if(transMap){
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		}
		if(pageNo<1){
			pageNo=1;
		}
		query.setFirstResult((pageNo-1)*pageSize);
		query.setMaxResults(pageSize);
		return query.list();
	}
	
	public <T>  List<T> findListBySql(String sql,int pageNo,int pageSize) {
		return findListBySql(sql,false,pageNo,pageSize);
	}

	public <T> List<T> findListBySql(String sql) {
		List<T> restList = null;
		if (sql != null) {
			Query query = getSession().createSQLQuery(sql);

			restList = query.list();
		}
		return restList;
	}

	public Pagination findPageByHql(String hql, Pagination pagination) {
		String countSql = getCountSql(hql,true);
		try {
			if (hql != null) {
				StringBuffer buffHSQL = new StringBuffer(hql);
				Query query = getSession().createQuery(buffHSQL.toString());
				query.setFirstResult((pagination.getPageNumber()-1)*pagination.getPageSize());
				query.setMaxResults(pagination.getPageSize());
				List<?> restList = query.list();
				pagination.setResultList(restList);
				if (countSql != null) {
					Query countQuery = getSession().createQuery(countSql);
					int count = Integer.parseInt(countQuery.uniqueResult().toString());
					pagination.setTotalCount(count);
				}
			} else {
				throw new BaseDaoException("请传入hql语句！");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new BaseDaoException(e.getCause().getMessage());
		}

		return pagination;
	}
	
	
	public <T> List<T> findListByHql(String hql) {
		Query query = getSession().createQuery(hql);
		return  query.list();
	}
	

	public Object findObjectByFieldsMap(Class<?> clazz, Map<String,Object> paraMap) {
		Criteria criteria = getSession().createCriteria(clazz);
		for (Iterator it = paraMap.keySet().iterator(); it.hasNext();) {
			String key = it.next().toString();
			criteria.add(Restrictions.eq(key, paraMap.get(key)));
		}
		criteria.setMaxResults(1);
		return criteria.uniqueResult();
	}

	public <T> List<T> getAllObject(Class<T> clazz) {
		return getHibernateTemplate().loadAll(clazz);
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public <T> List<T> callProcedure(final String procedureName, final Object[] os) {
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery query = session.createSQLQuery(procedureName);
				if (os != null) {
					for (int i = 0; i < os.length; i++) {
						query.setParameter(i, os[i]);
					}
				}
				return query.list();
			}
		});
	}

	@SuppressWarnings("deprecation")
	public Map<Integer, Object> callProcedure(String procedure,
			Map<Integer, Object> inParams, Map<Integer, Integer> outParams) {
		Map<Integer,Object> resultMap = new HashMap<Integer,Object>();
		Connection con = getSession().connection();
		try {
			CallableStatement cStatement = con.prepareCall(procedure);

			if (inParams != null) {
				Iterator<Integer> keyIt = inParams.keySet().iterator();
				while (keyIt.hasNext()) {
					Integer pos = (Integer) keyIt.next();
					cStatement.setObject(pos.intValue(), inParams.get(pos));
				}
			}

			if (outParams != null) {
				Iterator<Integer> keyIt = outParams.keySet().iterator();
				while (keyIt.hasNext()) {
					Integer pos = (Integer) keyIt.next();
					cStatement.registerOutParameter(pos.intValue(),
							((Integer) outParams.get(pos)).intValue());
				}
			}
			cStatement.executeUpdate();

			if (outParams != null) {
				Iterator<Integer> keyIt = outParams.keySet().iterator();
				while (keyIt.hasNext()) {
					Integer pos = (Integer) keyIt.next();
					resultMap.put(pos, cStatement.getObject(pos.intValue()));
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			try {
				con.rollback();
			} catch (Exception ex) {
				logger.error(ex.getMessage(),ex);
			}
			try {
				con = null;
			} catch (Exception ex) {
				logger.error(ex.getMessage(),ex);
			}
		} finally {
			try {
				con = null;
			} catch (Exception ex) {
				logger.error(ex.getMessage(),ex);
			}
		}
		return resultMap;
	}
	
	

	public String getCountSql(String sql,boolean isSql){
		String hql = sql;

		int fromIndex = hql.toLowerCase().indexOf("from");
		String projectionHql = hql.substring(0, fromIndex);

		hql = hql.substring(fromIndex);
		String rowCountHql = hql.replace("fetch", "");

		int index = rowCountHql.indexOf(" order ");
		if (index > 0) {
			rowCountHql = rowCountHql.substring(0, index);
		}
		if(projectionHql == null || projectionHql.equals("") || projectionHql.indexOf("select") == -1){
			return "select count(*) "+ rowCountHql;
		}
		
		if(!isSql){
			return "select count(0) from ("+sql+")";				//sql
		}
		
		if(rowCountHql.indexOf("group by")!=-1){
			return "select sum(count(*)/count(*)) "+rowCountHql;	//hql 
		}
		return "select count(*)" + rowCountHql;
	}
	

    public <T> List<T>    findListBySqlId(String sqlId,Map<String,Object> paraMap,ResultTransformer transformer){
        return ((List<T>)findListBySqlId( sqlId ,paraMap,transformer,null));
    }
    
    public <T> List<T>    findListBySqlId(String sqlId,Map<String,Object> paraMap,ResultTransformer transformer,AfterQueryHander afterHander){
        List<?> list = null;
        try {
            String hql = getSqlStatementById(sqlId, paraMap);
            Query query = getSession().createSQLQuery(hql);
            if(transformer!=null){
                query.setResultTransformer( transformer );
            }
            setQueryParameters(query, paraMap);
            list = query.list();
            if(afterHander!=null){
                return (List<T>)afterHander.doAfterQueryHander(list, sqlId, paraMap);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new BaseDaoException(e.getCause().getMessage());
        }
        return (List<T>)list;
    }

    public MybatisSqlSessionFactoryBean getSqlSessionFactoryBean()
    {
        return sqlSessionFactoryBean;
    }

    public void setSqlSessionFactoryBean( MybatisSqlSessionFactoryBean sqlSessionFactoryBean )
    {
        this.sqlSessionFactoryBean = sqlSessionFactoryBean;
    }
	
}