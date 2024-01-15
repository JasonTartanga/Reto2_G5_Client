package cipher;

/**
 *
 * @author Jason.
 */
import java.io.*;
import java.net.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Asimetric {

    public static void main(String[] args) {
        try {
            // Load Private Key
            FileInputStream fis = new FileInputStream(System.getProperty("user.home") + File.separator + "Documents\\CashTracker\\publicKey.key");
            byte[] publicKeyBytes = new byte[fis.available()];
            fis.read(publicKeyBytes);
            fis.close();

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);

            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
            // Client Socket
            Socket socket = new Socket("localhost", 1109);

            // Data Streams
            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

            // Encrypt and send data
            String message = "Me vengoo!";
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encryptedData = cipher.doFinal(message.getBytes());
            objectOutputStream.writeObject(encryptedData);

            objectOutputStream.close();
            socket.close();
        } catch (IOException | InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }
}
