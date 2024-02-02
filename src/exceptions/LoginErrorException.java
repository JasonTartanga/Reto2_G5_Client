package exceptions;

/**
 * Excepción de error de inicio de sesión.
 *
 * @author Jessica
 */
public class LoginErrorException extends Exception {

    /**
     * Creates a new instance of <code>LoginErrorException</code> without detail
     * message.
     */
    public LoginErrorException() {
    }

    /**
     * Constructs an instance of <code>LoginErrorException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public LoginErrorException(String msg) {
        super(msg);
    }
}
