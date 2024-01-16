package cipher;

/**
 *
 * @author Jason.
 */
import java.io.*;
import java.net.URLEncoder;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.MessageDigest;
import java.math.BigInteger;

public class Asimetric {

    private static final Logger log = Logger.getLogger(Asimetric.class.getName());

    public static String cipherPassword(String passwd) {
        FileInputStream fis = null;
        String cipherPasswd = null;

        try {
            // Cargamos la clave publica
            fis = new FileInputStream(System.getProperty("user.home") + File.separator + "Documents\\CashTracker\\publicKey.der");
            byte[] publicKeyBytes = new byte[fis.available()];
            fis.read(publicKeyBytes);
            fis.close();

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

            // Encriptamos la contraseña
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encryptedData = cipher.doFinal(passwd.getBytes());

            log.info("Contraseña cifrada --> " + cipherPasswd);
            //La convertimos en String y le damos la posiblidad de viajar mediante HTTP
            //cipherPasswd = Base64.getEncoder().encodeToString(encryptedData);
            //log.info("passwd cifrada sin URL --> " + cipherPasswd);
            //cipherPasswd = URLEncoder.encode(cipherPasswd, "UTF-8");
            cipherPasswd = calculateMD5(encryptedData);
            log.info("Contraseña cifrada --> " + cipherPasswd);

        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            log.severe(ex.getMessage());
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                log.severe(ex.getMessage());
            }
        }
        return cipherPasswd;
    }

    private static String calculateMD5(byte[] byteArray) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(byteArray);
            byte[] digest = md.digest();

            // Convertir el array de bytes a una representación hexadecimal
            BigInteger bigInt = new BigInteger(1, digest);
            String md5Hash = bigInt.toString(16);

            // Asegurarse de que la cadena tenga 32 caracteres
            while (md5Hash.length() < 32) {
                md5Hash = "0" + md5Hash;
            }

            return md5Hash;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
