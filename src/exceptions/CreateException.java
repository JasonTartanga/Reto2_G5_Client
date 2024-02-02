package exceptions;

/**
 * Gestiona excepciones a la hora de crear entidades.
 *
 * @author Jason.
 */
public class CreateException extends Exception {

    /**
     * Creates a new instance of <code>CreateException</code> without detail
     * message.
     */
    public CreateException() {
    }

    /**
     * Constructs an instance of <code>CreateException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public CreateException(String msg) {
        super(msg);
    }
}
