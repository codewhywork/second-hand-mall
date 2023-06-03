package com.bistu.why.common.utils;

import java.security.MessageDigest;

public class PasswordEncoder {

	// 十六进制数字
	private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

	private final static String salt = "why.bistu.cn";
	private final static String algorithm = "MD5";

	/**
	 * 转换字节数组为16进制字串
	 *
	 * @param b 字节数组
	 * @return 16进制字串
	 */
	private static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	public static String encode(String rawPass) {
		String result = null;
		try {
			MessageDigest md = MessageDigest.getInstance(algorithm);
			// 加密后的字符串
			result = byteArrayToHexString(md.digest(mergePasswordAndSalt(
					rawPass).getBytes("utf-8")));
		} catch (Exception ex) {
		}
		return result;
	}
/*验证两次密码是否相同*/
	public static boolean isPasswordValid(String encPass, String rawPass) {
		String pass1 = "" + encPass;
		String pass2 = encode(rawPass);
		return pass1.equals(pass2);
	}

	private static String mergePasswordAndSalt(String password) {
		if (password == null) {
			password = "";
		}
		if ((salt == null) || "".equals(salt)) {
			return password;
		} else {
			return password + "{" + salt.toString() + "}";
		}
	}

//	public static void main(String[] args) {
//		// 盐值自己定
//		String salt = "helloworld";
//		PasswordEncoder encoderMd5 = new PasswordEncoder(salt, "MD5");
//		String encode = encoderMd5.encode("test");
//		// 带盐值的MD5加密
//		System.out.println("带盐值的MD5加密" + encode);
//		// 空盐值的MD5加密
//		System.out.println("空盐值的MD5加密"
//				+ new PasswordEncoder(null, "MD5").encode("test"));
//		boolean passwordValid = encoderMd5.isPasswordValid(
//				"1bd98ed329aebc7b2f89424b5a38926e", "test");
//		System.out.println(passwordValid);
//
//		PasswordEncoder encoderSha = new PasswordEncoder(salt, "SHA");
//		String pass2 = encoderSha.encode("test");
//		System.out.println(pass2);
//		boolean passwordValid2 = encoderSha.isPasswordValid(
//				"1bd98ed329aebc7b2f89424b5a38926e", "test");
//		System.out.println(passwordValid2);
//	}

}

