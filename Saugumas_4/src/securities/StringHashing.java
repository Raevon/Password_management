package securities;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

/**
 * PBKDF2 hashinimas
 */
public class StringHashing {
    public static String generateSalt(){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return getBytesToString(salt);
    }

    public static String generateHash(String password, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] bytes = factory.generateSecret(spec).getEncoded();
        return getBytesToString(bytes);
    }

    public static boolean validateHash(String password, String salt, String hash) throws InvalidKeySpecException, NoSuchAlgorithmException {
        System.out.println(hash);
        System.out.println(generateHash(password, salt));

        if (hash.equals(generateHash(password, salt))){
            System.out.println("Done");
            return true;
        }
        return false;
    }

    public static String getBytesToString(byte[] values){
        String value = "";
        for(byte b : values){
            value += (int)b;
        }
        return value;
    }
}
