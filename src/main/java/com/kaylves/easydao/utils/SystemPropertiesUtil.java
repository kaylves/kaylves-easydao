package com.kaylves.easydao.utils;

import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SystemPropertiesUtil {
	private static SystemPropertiesUtil instance = new SystemPropertiesUtil();
	private static Properties properties;
	protected final Log logger = LogFactory.getLog(getClass());

	protected void loadProperties() {
		try {
			
			properties = new Properties();
			ClassLoader loader = Thread.currentThread().getContextClassLoader();

			if (loader == null) {
				loader = instance.getClass().getClassLoader();
			}

			InputStream is = loader.getResourceAsStream("system.properties");
			
			if (is != null) {
				properties.load(is);
				is.close();
			} else {
				this.logger.error("SysPropertiesUtil can not load property files!");
			}
		} catch (Exception e) {
			this.logger.error(e);
			e.printStackTrace();
		}
	}

	public static String getProperty(String key) {
		if (properties == null) {
			instance.loadProperties();
		}
		return properties.getProperty(key);
	}

	public static int getInt(String sPropertyName, int iDefaultValue) {
		try {
			String sProperty = getProperty(sPropertyName);
			return Integer.parseInt(sProperty);
		} catch (Exception e) {
		}
		return iDefaultValue;
	}

	public static String getString(String sPropertyName, String sDefaultValue) {
		try {
			return getProperty(sPropertyName);
		} catch (Exception e) {
		}
		return sDefaultValue;
	}

	public static boolean getBoolean(String key, boolean bDefaultValue) {
		try {
			String s = getProperty(key);
			if (s != null) {
				return (s.equalsIgnoreCase("true"))|| (s.equalsIgnoreCase("t"));
			}
			return bDefaultValue;
		} catch (Exception e) {
		}
		return bDefaultValue;
	}


	public static void main(String[] args) {
		String property = getProperty("system.mail.smtp.host");
		System.out.println(property);
	}
}