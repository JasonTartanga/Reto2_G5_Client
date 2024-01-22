package exceptions;

/**
 * Excepción para error en el servidor.
 * @author Ian.
 */
public class ServerErrorException extends Exception {

    /**
     * Creates a new instance of <code>ErrorDeServidor</code> without detail
     * message.
     */
    public ServerErrorException() {
    }

    /**
     * Constructs an instance of <code>ErrorDeServidor</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public ServerErrorException(String msg) {
        super(msg);
    }
}
