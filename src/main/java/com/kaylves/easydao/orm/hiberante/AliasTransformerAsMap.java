package com.kaylves.easydao.orm.hiberante;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.hibernate.transform.BasicTransformerAdapter;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  kaylves
 * @version  [版本号, 2014年11月22日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class AliasTransformerAsMap implements Serializable {

    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 结果集转map
     * the key like user_name the result is userName
     * 即将key转成java 的命名规范
     */
    public static final AliasTransformerAsBeanMap TRANSFORMER_AS__MAP = new AliasTransformerAsBeanMap();
    
    /**
     * 结果集转Map
     * the key like 
     * user_name the result key is user_name，即将key 转成小写
     */
    public static final AliasTransformerLowerMap TRANSFORMER_AS_LOWER_MAP = new AliasTransformerLowerMap();



    /**
     * <一句话功能简述>
     * <功能详细描述>
     * @param columnName
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static String convertColumnToProperty( String columnName )
    {
        columnName = columnName.toLowerCase();
        StringBuffer buff = new StringBuffer( columnName.length() );
        StringTokenizer st = new StringTokenizer( columnName, "_" );
        while ( st.hasMoreTokens() )
        {
            buff.append( StringUtils.capitalize( st.nextToken() ) );
        }
        buff.setCharAt( 0, Character.toLowerCase( buff.charAt( 0 ) ) );
        return buff.toString();
    }

    
    /**
     * 
     * <一句话功能简述>结果集中转成Map key命名符合java命名规范
     * <功能详细描述>
     * 
     * @author  kaylves
     * @version  [版本号, 2014年11月24日]
     * @see  [相关类/方法]
     * @since  [产品/模块版本]
     */
    static class AliasTransformerAsBeanMap extends BasicTransformerAdapter{

        /**
         * 注释内容
         */
        private static final long serialVersionUID = 1L;
        
        /**
         * {@inheritDoc}
         */
        public Object transformTuple(Object[] tuple, String[] aliases) {
            Map<String,Object> result = new HashMap<String,Object>(tuple.length);
            for ( int i=0; i<tuple.length; i++ ) {
                String alias = aliases[i];
                if ( alias!=null ) {
                    result.put( convertColumnToProperty(alias.toLowerCase()), tuple[i] );
                }
            }
            return result;
        }
        
    }
    
    /**
     * 
     * <一句话功能简述>结果集中转成Map key 是小写的
     * <功能详细描述>
     * 
     * @author  kaylves
     * @version  [版本号, 2014年11月24日]
     * @see  [相关类/方法]
     * @since  [产品/模块版本]
     */
    static class AliasTransformerLowerMap extends BasicTransformerAdapter{
        
        /**
         * 注释内容
         */
        private static final long serialVersionUID = 1L;
        
        public Object transformTuple(Object[] tuple, String[] aliases) {
            Map<String,Object> result = new HashMap<String,Object>(tuple.length);
            for ( int i=0; i<tuple.length; i++ ) {
                String alias = aliases[i];
                if ( alias!=null ) {
                    result.put(alias.toLowerCase(), tuple[i] );
                }
            }
            return result;
        }
        
    }
}
