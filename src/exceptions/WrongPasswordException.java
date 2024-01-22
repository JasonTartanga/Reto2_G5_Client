package exceptions;

/**
 * Excepción de contraseña incorrecta.
 *
 * @author Jessica
 */
public class WrongPasswordException extends Exception {

    /**
     * Creates a new instance of <code>WrongPasswordException</code> without
     * detail message.
     */
    public WrongPasswordException() {
    }

    /**
     * Constructs an instance of <code>WrongPasswordException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public WrongPasswordException(String msg) {
        super(msg);
    }
}
