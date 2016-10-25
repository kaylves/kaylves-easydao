package com.kaylves.easydao.test;



import com.kaylves.easydao.utils.security.Base64;
import com.kaylves.easydao.utils.security.CertificateCoder;
import com.kaylves.easydao.utils.security.CertificateHelper;




public class Test {
	private static String password = "qwer@#2012";
	private static String alias = "js.189.cn";
	private static String certificatePath = "d:/jszt.cer";
	private static String keyStorePath = "d:/jszt.keystore";

	public static void test() throws Exception {
		System.err.println("公钥加密——私钥解密");
		String inputStr = "Ceritifcate";
		byte[] data = inputStr.getBytes();

		byte[] encrypt = CertificateCoder.encryptByPublicKey(data,
				certificatePath);

		byte[] decrypt = CertificateCoder.decryptByPrivateKey(encrypt,
				keyStorePath, alias, password);
		String outputStr = new String(decrypt);

		System.err.println("加密前: " + inputStr + "\n\r" + "解密后: " + outputStr);

	}

	public static void testSign() throws Exception {
		System.err.println("私钥加密——公钥解密");

		String inputStr = "sign";
		byte[] data = inputStr.getBytes();

		byte[] encodedData = CertificateCoder.encryptByPrivateKey(data,keyStorePath, alias, password);

		byte[] decodedData = CertificateCoder.decryptByPublicKey(encodedData,
				certificatePath);

		String outputStr = new String(decodedData);
		System.err.println("加密前: " + inputStr + "\n\r" + "解密后: " + outputStr);

		System.err.println("私钥签名——公钥验证签名");
		// 产生签名
		String sign = CertificateCoder.sign(encodedData, keyStorePath, alias,
				password);
		System.err.println("签名:\r" + sign);

		// 验证签名
		boolean status = CertificateCoder.verify(encodedData, sign,
				certificatePath);
		System.err.println("状态:\r" + status);
	}
	
	public static void test2()
	{
		String testStr="哈哈测试下abc@#1234";
		try {
			byte[] enc=CertificateHelper.encryptByPrivateKey(testStr.getBytes());
			String encBase64=Base64.encode(enc);
			System.out.println(encBase64);
			byte[] decBase64=Base64.decode(encBase64);
			byte[] decodedData = CertificateCoder.decryptByPublicKey(decBase64,
					certificatePath);
			System.out.println(new String(decodedData));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] arsg)
	{
		try {
			test2();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
