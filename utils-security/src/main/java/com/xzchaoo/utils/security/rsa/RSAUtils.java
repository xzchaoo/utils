package com.xzchaoo.utils.security.rsa;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * Created by Administrator on 2016/10/31.
 */
public class RSAUtils {

	public static KeyPair generateDefaultPublicKey() throws NoSuchAlgorithmException {
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		return kpg.generateKeyPair();
	}

	public static PublicKey getPublicKey(byte[] encodedKey) throws InvalidKeySpecException, NoSuchAlgorithmException {
		return KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(encodedKey));
	}

	public static PrivateKey getPrivateKey(byte[] encodedKey) throws InvalidKeySpecException, NoSuchAlgorithmException {
		return KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
	}

	public static byte[] encrypt(byte[] data, Key key) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException {
		Cipher localCipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING");
		localCipher.init(Cipher.ENCRYPT_MODE, key);
		return localCipher.doFinal(data);
	}

	public static byte[] decrypt(byte[] encryptedData, Key key) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException {
		Cipher localCipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING");
		localCipher.init(Cipher.DECRYPT_MODE, key);
		return localCipher.doFinal(encryptedData);
	}

}
