package com.gesLaboral.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Cifrado {
	/**
	 * Método para encriptar la contraseña a md5
	 * 
	 * @param password
	 *            es la contraseña a encriptar
	 * @return la contraseña encriptada o null sino ha sido posible encriptada
	 */
	public static String md5(String password) {
		try {
			if (password != null && !password.trim().isEmpty()) {

				MessageDigest md = MessageDigest.getInstance("MD5");
				md.reset();
				md.update(password.getBytes());
				byte bytes[] = md.digest();
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < bytes.length; i++) {
					String hex = Integer.toHexString(0xff & bytes[i]);
					if (hex.length() == 1) {
						sb.append('0');
					}
					sb.append(hex);
				}

				return sb.toString();
			}
		} catch (NoSuchAlgorithmException e) {

			e.printStackTrace();
			return null;

		}
		return null;
	}
}
