package cipher;

/**
 *
 * @author Jason.
 */
import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.xml.bind.DatatypeConverter;

public class Asimetric {

    private static final Logger LOG = Logger.getLogger(Asimetric.class.getName());

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

            cipherPasswd = DatatypeConverter.printHexBinary(encryptedData);

        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            LOG.severe(ex.getMessage());
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException ex) {
                LOG.severe(ex.getMessage());
            }
        }
        return cipherPasswd;
    }
}
