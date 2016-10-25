
package com.kaylves.easydao.utils.httpclient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/**
 * 
 * Copyright(c) 2013 hofort. All Rights Reserved.
 * Compiler: JDK1.6.0_23
 * @author Kaylves
 * @create_date 2014-6-20 下午04:50:01
 * @version 1.0
 * @update_user Kaylves
 * @update_date 2014-6-20 下午04:50:01
 * @description 此处写类的描述
 */
public final class HttpClientUtils {
	
	/**
	 * Logger
	 */
	private static Logger logger = Logger.getLogger(HttpClientUtils.class);

	/**
	 * 连接时间
	 */
	private static final int CONNECTION_TIME_OUT = 5000;

	private static final int SO_TIME_OUT = 65000;

	private static BasicHttpParams bp;

	private static SchemeRegistry schreg;

	private static PoolingClientConnectionManager pccm;
	
	static {
		bp = new BasicHttpParams();
		
		HttpConnectionParams.setConnectionTimeout(bp, CONNECTION_TIME_OUT); // 超时时间设置
		HttpConnectionParams.setSoTimeout(bp, SO_TIME_OUT);
		// 设置访问协议
		schreg = new SchemeRegistry();
		schreg.register(new Scheme("http", 80, 		PlainSocketFactory.getSocketFactory()));
		schreg.register(new Scheme("https", 443, 	SSLSocketFactory.getSocketFactory()));

		// 多连接的线程安全的管理器
		pccm = new PoolingClientConnectionManager(schreg);
		pccm.setDefaultMaxPerRoute(200); 					// 每个主机的最大并行链接数
		pccm.setMaxTotal(600);
	}
	

	/**
	 * 
	 * @author Kaylves
	 * @time 2014-6-20 下午04:57:56
	 * @param actionUrl
	 * @param params
	 * @return
	 * String
	 * @description
	 * @version 1.0
	 */
	public static String post(String actionUrl, Map<String, String> params) {
		HttpClient httpclient = new DefaultHttpClient(pccm, bp);
		HttpPost httpPost = new HttpPost(actionUrl);
		Object timeLimit = null;
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		for (Map.Entry<String, String> entry : params.entrySet()) {// 构建表单字段内容
			String key = entry.getKey();
			String value = entry.getValue();
			JSONObject json = JSONObject.fromObject(value);
			timeLimit = json.get("timeLimit");
			list.add(new BasicNameValuePair(key, value));
		}
		if (timeLimit != null) {
			int time = Integer.parseInt(timeLimit.toString());
			HttpConnectionParams.setConnectionTimeout(bp, time);                 // 超时时间设置
			HttpConnectionParams.setSoTimeout(bp, time);
		} else {
			HttpConnectionParams.setConnectionTimeout(bp, CONNECTION_TIME_OUT); // 超时时间设置
			HttpConnectionParams.setSoTimeout(bp, SO_TIME_OUT);
		}
		HttpResponse httpResponse;
		String responseString = "";
		logger.warn("传入后台的参数：" + list);
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(list, "UTF-8"));
			httpResponse = httpclient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				responseString = EntityUtils.toString(httpResponse.getEntity());
				return responseString;
			} else if (httpResponse.getStatusLine().getStatusCode() == 404) {
				logger.warn("actionUrl:{} not found 404!" + actionUrl);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			httpPost.releaseConnection();
		}
		return null;
	}

	
	public static String post2(String actionUrl, Map<String, String> params) {
		HttpClient httpclient = new DefaultHttpClient(pccm, bp);
		HttpPost httpPost = new HttpPost(actionUrl);
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		for (Map.Entry<String, String> entry : params.entrySet()) {// 构建表单字段内容
			String key = entry.getKey();
			String value = entry.getValue();
			list.add(new BasicNameValuePair(key, value));
		}
		HttpResponse httpResponse;
		String responseString = "";
		logger.warn("传入后台的参数：" + list);
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(list, "UTF-8"));
			httpResponse = httpclient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				responseString = EntityUtils.toString(httpResponse.getEntity());
				return responseString;
			} else if (httpResponse.getStatusLine().getStatusCode() == 404) {
				logger.warn("actionUrl:{} not found 404!" + actionUrl);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			httpPost.releaseConnection();
		}
		return null;
	}


	
	public static String postWithHeader(String data,
			Map<String, String> header, String actionUrl) throws Exception {
		InputStream in = null;
		java.io.BufferedReader breader = null;
		try {
			URL url = new URL(actionUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("Connection", "Keep-Alive");
			connection.setRequestProperty("Cache-Control","no-cache");
			connection.setRequestProperty("Accept", "*/*");
			connection.setRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows 2000)");
			connection.setRequestProperty("Accept-Language", "zh-cn");
			connection.setRequestProperty("Accept-Encoding", "gzip, deflate");
			connection.setRequestProperty("Content-Length", String.valueOf(data
					.length()));
			for (Map.Entry<String, String> entry : header.entrySet()) {
				connection.setRequestProperty(entry.getKey(), entry.getValue());
			}
			connection.setConnectTimeout(5000);
			connection.setDoOutput(true);
			connection.connect();
			connection.getOutputStream().write(data.getBytes());
			if (connection.getResponseCode() == 200) {
				in = connection.getInputStream();
				breader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
				String str = breader.readLine();
				StringBuffer sb = new StringBuffer();
				while (str != null) {
					sb.append(str);
					str = breader.readLine();
				}
				return sb.toString();
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (null != breader) {
				breader.close();
			}
			if (null != in) {
				in.close();
			}
		}
		return "";
	}

	public static String getWithHeader(String data, Map<String, String> header,String actionUrl) throws Exception {
		InputStream in = null;
		java.io.BufferedReader breader = null;
		try {
			URL url = new URL(actionUrl);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setRequestProperty("Connection", "Keep-Alive");
			connection.setRequestProperty("Cache-Control", "no-cache");
			connection.setRequestProperty("Accept", "*/*");
			connection.setRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows 2000)");
			connection.setRequestProperty("Accept-Language", "zh-cn");
			connection.setRequestProperty("Accept-Encoding", "gzip, deflate");
			connection.setRequestProperty("Content-Length", String.valueOf(data.length()));
			
			for (Map.Entry<String, String> entry : header.entrySet()) {
				connection.setRequestProperty(entry.getKey(), entry.getValue());
			}
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(5000);
			connection.setDoOutput(true);
			connection.connect();
			connection.getOutputStream().write(data.getBytes());
			if (connection.getResponseCode() == 200) {
				in = connection.getInputStream();
				breader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
				String str = breader.readLine();
				StringBuffer sb = new StringBuffer();
				while (str != null) {
					sb.append(str);
					str = breader.readLine();
				}
				return sb.toString();
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (null != breader) {
				breader.close();
			}
			if (null != in) {
				in.close();
			}
		}
		return "";
	}

	public static String get2(String url, String model,
			Map<String, String> params) {
		BasicHttpParams bp = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(bp, CONNECTION_TIME_OUT); 	// 超时时间设置
		HttpConnectionParams.setSoTimeout(bp, SO_TIME_OUT);
		HttpClient httpclient = new DefaultHttpClient(bp);
		HttpGet httpGet = new HttpGet(url);
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		for (Map.Entry<String, String> entry : params.entrySet()) {			// 构建表单字段内容
			list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		logger.warn("传入后台的参数" + list);
		httpGet.setHeader("User-Agent", model);
		try {
			httpclient.getParams().setParameter("utf-8", "UTF-8");
			HttpResponse httpResponse = httpclient.execute(httpGet);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				return EntityUtils.toString(httpResponse.getEntity());
			} else if (httpResponse.getStatusLine().getStatusCode() == 404) {
				logger.warn("actionUrl:{} not found 404!" + url);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		return null;

	}

}
