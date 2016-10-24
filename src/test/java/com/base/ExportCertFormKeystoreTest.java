package com.base;

import com.ycii.core.utils.security.ExportCertFormKeystore;
/**
 * Copyright(c) 2013 hofort. All Rights Reserved.
 * Compiler: JDK1.6.0_23
 * @author Kaylves
 * @create_date 2014-6-24 下午10:11:23
 * @version 1.0
 * @update_user Kaylves
 * @update_date 2014-6-24 下午10:11:23
 * @description 此处写类的描述
 */
public class ExportCertFormKeystoreTest {
	public void genkeyTest() {
		// 生成密钥测试
		new ExportCertFormKeystore().genkey();
	}

	public void exportTest() {
		// 导出证书文件测试
		new ExportCertFormKeystore().export();
	}
}
