package com.ycii.core.utils.security;

import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
/**
 * 
 * Copyright(c) 2013 hofort. All Rights Reserved.
 * Compiler: JDK1.6.0_23
 * @author Kaylves
 * @create_date 2014-6-20 下午04:52:44
 * @version 1.0
 * @update_user Kaylves
 * @update_date 2014-6-20 下午04:52:44
 * @description AES加密解密工具类
 */
public class AES {

	public static byte[] ENC = { 104, 116, 116, 112, 58, 47, 47, 49, 57, 50,
			46, 49, 54, 56, 46, 49, 54, 46, 49, 51, 54, 58, 56, 48, 53, 53, 47,
			71, 101, 116, 66, 105, 108, 108, 46, 97, 115, 109, 120, 47 };

	private static String AES = "AES";

	private static String ENCODING = "UTF-8";

	// key =1234567890123456

	private static SecretKeySpec createKey(String password) {
		byte[] data = null;
		try {
			data = password.getBytes(ENCODING);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return new SecretKeySpec(data, AES);
	}

	public static byte[] decrypt(byte[] content, String password) {
		try {
			SecretKeySpec key = createKey(password);
			Cipher cipher = Cipher.getInstance(AES);// 创建密码器
			cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(content);
			return result; // 加密
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] encrypt(String content, String password) {
		try {
			SecretKeySpec key = createKey(password);
			Cipher cipher = Cipher.getInstance(AES);// 创建密码器
			byte[] byteContent = content.getBytes(ENCODING);
			cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(byteContent);
			return result; // 加密
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		String kayvles = "kaylves";
		byte[] b=encrypt(kayvles,"1234567890123456");
		System.out.println(b);
		
	}
}
