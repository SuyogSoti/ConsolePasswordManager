package passwordManager;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * Encryption
 */
public class Encryption {
    static String ENCRYPTION_ALGORITHM = "AES";
    static int ENCRYPTION_PASSWORD_LENGTH = 16;
    private byte[] encryptionPassword;

    Encryption(String encryptionPassword) {
        this.encryptionPassword = this.processEncryptionKey(encryptionPassword);
    }

    public byte[] encrypt(String content) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(Encryption.ENCRYPTION_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE,
                new SecretKeySpec(this.encryptionPassword, Encryption.ENCRYPTION_ALGORITHM));
        return cipher.doFinal(content.getBytes());
    }

    public String decrypt(byte[] content) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(Encryption.ENCRYPTION_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE,
                new SecretKeySpec(this.encryptionPassword, Encryption.ENCRYPTION_ALGORITHM));
        return new String(cipher.doFinal(content));
    }

    private byte[] processEncryptionKey(String s) {
        int length = Encryption.ENCRYPTION_PASSWORD_LENGTH;
        if (s.length() < length) {
			int missingLength = length - s.length();
			for (int i = 0; i < missingLength; i++) {
				s += " ";
			}
		}
		return s.substring(0, length).getBytes();
    }
}