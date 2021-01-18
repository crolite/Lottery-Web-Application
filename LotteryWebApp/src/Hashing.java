import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Hashing {
    //byte[] salt=generateSalt();


    public byte[] generateSalt() {  // creates salt
        SecureRandom random = new SecureRandom();// uses rng for salt
        byte bytes[] = new byte[16];
        random.nextBytes(bytes);// Byte array filled with random numbers
        return bytes;
    }


    public String hashpassword(String password, byte[] salt) throws NoSuchAlgorithmException {
        String algorithm="SHA-256"; // Algorithm used for hashing
        MessageDigest digest= MessageDigest.getInstance(algorithm);
        digest.reset();  //Original password hashed
        digest.update(salt); //Orginal password with salt hashed
        byte[] hash= digest.digest(password.getBytes());

        return bytestoStringHex(hash);
    }
    private final static char[] hexArray="0123456789ABCDEF".toCharArray();

    public static String bytestoStringHex(byte[] bytes){// algorithm used for converting  bytes to hexadecimal values
        char[] hexChars=new char[bytes.length*2];
        for (int j=0;j<bytes.length;j++){
            int v= bytes[j]& 0xFF;
            hexChars[j*2]= hexArray[v >>> 4];
            hexChars[j*2+1]=hexArray[v&0x0F];
        }
        return new String(hexChars);
    }


}
