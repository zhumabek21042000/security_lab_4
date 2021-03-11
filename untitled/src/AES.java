import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.SecretKeySpec;

public class AES {

	private static SecretKeySpec getSecretKey(String key) {

		try {
			MessageDigest sha = MessageDigest.getInstance("SHA-1");
			byte[] keyBytes = sha.digest(key.getBytes("UTF-8"));
			keyBytes = Arrays.copyOf(keyBytes, 16);
			return new SecretKeySpec(keyBytes, "AES");
		} catch (Exception e) { e.printStackTrace(); }

		return null;

	}
	
	public static String encrypt(String plaintext, int key) {

		try {
			SecretKeySpec secretKey = getSecretKey(String.valueOf(key));
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			return Base64.getEncoder().encodeToString(cipher.doFinal(plaintext.getBytes("UTF-8")));
		} catch (Exception e) { e.printStackTrace(); }

		return plaintext;

	}
	
	public static String decrypt(String ciphertext, int key) {

		try {
			SecretKeySpec secretKey = getSecretKey(String.valueOf(key));
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			return new String(cipher.doFinal(Base64.getDecoder().decode(ciphertext)));
		} catch (IllegalBlockSizeException e) {
			System.out.println("decryption failed / wrong key - IllegalBlockSizeException");
		} catch (BadPaddingException e) {
			System.out.println("decryption failed / wrong key - BadPaddingException");
		} catch (InvalidKeyException e) {
			System.out.println("decryption failed / wrong key - InvalidKeyException");
		} catch (Exception e) { e.printStackTrace(); }
		
		return ciphertext;
		
	}
	
}
