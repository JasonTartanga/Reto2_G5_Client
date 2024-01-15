package cipher;

/**
 *
 * @author Jason.
 */
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Asimetric {

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

            cipherPasswd = Base64.getEncoder().encodeToString(encryptedData);
            cipherPasswd = URLEncoder.encode(cipherPasswd, "UTF-8");

            System.out.println("Contraseña cifrada --> " + cipherPasswd);
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(Asimetric.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                Logger.getLogger(Asimetric.class.getName()).log(Level.SEVERE, null, ex);
            }
            return cipherPasswd;
        }
    }
}
