package com.ycii.core.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

/**
 * <一句话功能简述>Bean 工具类
 * <功能详细描述>
 * 
 * @author  kaylves
 * @version  [版本号, 2015年1月15日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class BeanUtil extends BeanUtils{
    
    protected static final Logger  logger = Logger.getLogger( BeanUtil.class );
    

	/**
	 * 
	 * @param dis	待复制的类
	 * @param target	复制类的数据来源	
	 */
	public static void copyProperties(Object dis,Object target){
		try {
			BeanUtils.copyProperties(dis, target);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public  static Map<String,Object> convertBean2Map(Object object){
		Map<String,Object> map = null;
		try {
			map=  PropertyUtils.describe(object);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		};
		return map;
	}
	public static Object getBeanProperty(Object bean,String name){
		Object value=null;
		try {
			value=BeanUtils.getProperty(bean, name);
		} catch (IllegalAccessException e) {
			value=null;
		} catch (InvocationTargetException e) {
			value=null;
		} catch (NoSuchMethodException e) {
			value=null;
		}
		return  value;
	}
	
	/**
	 * <一句话功能简述>将Java Bean转换为Map
	 * <功能详细描述>将Java Bean转换为Map,key是Java Bean的属性名称,value是JavaBean的属性值
	 * @param bean
	 * @return [参数说明]
	 * 
	 * @return Map<String,Object> [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	@SuppressWarnings( "unchecked" )
    public static Map<String,Object> describe(Object bean){
	    try
        {
            return PropertyUtils.describe( bean );
        }  catch ( Exception e )
        {
            //不做处理，返回NULL
            logger.error(e.getMessage(),e);
        }
        return null;
	    
	};
	

}
