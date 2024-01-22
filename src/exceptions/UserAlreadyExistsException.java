package exceptions;

/**
 * Excepción para usuario ya existente.
 *
 * @author Ian.
 */
public class UserAlreadyExistsException extends Exception {

    /**
     * Creates a new instance of <code>UsuarioYaExiste</code> without detail
     * message.
     */
    public UserAlreadyExistsException() {
    }

    /**
     * Constructs an instance of <code>UsuarioYaExiste</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public UserAlreadyExistsException(String msg) {
        super(msg);
    }
}
