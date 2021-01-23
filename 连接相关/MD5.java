/*
 * 登录时，把输入的密码进行加密，然后把密文和数据库的密码进行匹配
 * 如果匹配成功，则可以登录，否则登录不成功
 */
package com.rong.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
	public static String encryByMd5(String conText) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");// 获取MD5实例
			md.update(conText.getBytes());// 此处传入要加密的byte类型值
			byte[] digest = md.digest();// 此处得到的是md5加密后的byte类型值

			int i;
			StringBuilder sb = new StringBuilder();
			for (int offset = 0; offset < digest.length; offset++) {
				i = digest[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					sb.append(0);
				sb.append(Integer.toHexString(i));// 通过Integer.toHexString方法把值变为16进制
			}
			return sb.toString();
//			System.out.println("32位结果:" + sb.toString());// 从下标0开始，length目的是截取多少长度的值
//			System.out.println("64位结果:" + sb.toString().substring(8, 24));

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
//		MD5 md5 = new MD5();
		System.out.println(MD5.encryByMd5("e10adc3949ba59abbe56e057f20f883e"));
		;
	}

}