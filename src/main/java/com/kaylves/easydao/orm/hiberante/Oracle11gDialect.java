package com.kaylves.easydao.orm.hiberante;

import org.hibernate.Hibernate;
import org.hibernate.dialect.Oracle10gDialect;
import org.hibernate.dialect.function.StandardSQLFunction;

/**
 * <一句话功能简述>重写Oracle分页
 * <功能详细描述>
 * 
 * @author  kaylves
 * @version  [版本号, 2014年12月17日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class Oracle11gDialect extends Oracle10gDialect
{
    
    

    public Oracle11gDialect()
    {
        
        super();
      
    
        registerFunction("abs", new StandardSQLFunction("abs") );
        registerFunction("round", new StandardSQLFunction("round", Hibernate.BIG_DECIMAL) );
        registerFunction("ceil", new StandardSQLFunction("ceil", Hibernate.BIG_DECIMAL) );
        registerFunction("floor", new StandardSQLFunction("floor", Hibernate.BIG_DECIMAL) );
    }

    public String getLimitString(String sql, boolean hasOffset) {
        
        sql = sql.trim();
        boolean isForUpdate = false;
        if ( sql.toLowerCase().endsWith(" for update") ) {
            sql = sql.substring( 0, sql.length()-11 );
            isForUpdate = true;
        }

        StringBuffer pagingSelect = new StringBuffer( sql.length()+100 );
        
        if (hasOffset) {
            pagingSelect.append("select * from ( select row_.*, rownum rownum_ from ( ");
        }
        else {
           pagingSelect.append("select * from ( select row_.*, rownum rownum_ from ( ");

        }
        pagingSelect.append(sql);
        if (hasOffset) {
            pagingSelect.append(" ) row_ where rownum <= ?) where rownum_ > ?");
        }
        else {
            
            pagingSelect.append(" ) row_  ) where rownum_<= ?");

        }

        if ( isForUpdate ) {
            pagingSelect.append( " for update" );
        }

        return pagingSelect.toString();
    }
    
}
