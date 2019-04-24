package com;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class Crypto_test {

	

	public static void main(String[] arg) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
		KeyGenerator gen = KeyGenerator.getInstance("AES");

		SecretKey sec = gen.generateKey();
		
		String a = "why wont this work";

		byte [] temp = a.getBytes("UTF-8");
		
		System.out.println(a);

		byte [] encrypt = encrypt(temp, sec);
		
		System.out.println(new String(encrypt, "UTF8"));
		
		byte [] u = decrypt(encrypt, sec);
		
		System.out.println(new String(u, "UTF8"));
		
	}
	
	private static byte [] encrypt(byte [] data, SecretKey sec) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

		Cipher ciph = Cipher.getInstance("AES/ECB/PKCS5Padding");
		ciph.init(Cipher.ENCRYPT_MODE, sec);
		
		return ciph.doFinal(data);	
	}
	
	private static byte [] decrypt(byte [] data, SecretKey sec) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		Cipher ciph = Cipher.getInstance("AES/ECB/PKCS5Padding");
		ciph.init(Cipher.DECRYPT_MODE, sec);
		
		return ciph.doFinal(data);
	}
}
