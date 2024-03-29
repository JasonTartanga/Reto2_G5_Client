package cipher;

/**
 * Esta clase permite cifrar Asimetricamente mediante clave publica.
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

    /**
     * Cifra un String y lo formatea para que pueda viajar mediante HTTP.
     *
     * @param passwd el String que se quiere cifrar.
     * @return el String cifrado con formato para poder enviarse mediante HTTP.
     */
    public static String cipherPassword(String passwd) {
        FileInputStream fis = null;
        String cipherPasswd = null;

        try {
            try {
                fis = new FileInputStream(System.getProperty("user.home") + File.separator + "Documents\\CashTracker\\publicKey.der");
            } catch (FileNotFoundException e) {
                fis = new FileInputStream("src/cipher/publicKey.der");

            }
            byte[] publicKeyBytes = new byte[fis.available()];
            fis.read(publicKeyBytes);
            fis.close();

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encryptedData = cipher.doFinal(passwd.getBytes());

            cipherPasswd = DatatypeConverter.printHexBinary(encryptedData);

        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            ex.printStackTrace();
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
