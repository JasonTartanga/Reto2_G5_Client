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
import javax.xml.bind.DatatypeConverter;

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

            log.info("encriptedData --> " + encryptedData);
            //La convertimos en String y le damos la posiblidad de viajar mediante HTTP
            //cipherPasswd = Base64.getEncoder().encodeToString(encryptedData);
            //log.info("passwd cifrada sin URL --> " + cipherPasswd);
            //cipherPasswd = URLEncoder.encode(cipherPasswd, "UTF-8");
            cipherPasswd = DatatypeConverter.printHexBinary(encryptedData);
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

    public static String bytesToHex(byte[] bytes) {
        // Convierte el arreglo de bytes a un objeto BigInteger
        BigInteger bigInt = new BigInteger(1, bytes);

        // Convierte el BigInteger a una cadena hexadecimal
        String hexString = bigInt.toString(16);

        // Asegura que la cadena tenga la longitud adecuada
        int paddingLength = (bytes.length * 2) - hexString.length();
        if (paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hexString;
        } else {
            return hexString;
        }
    }
}
