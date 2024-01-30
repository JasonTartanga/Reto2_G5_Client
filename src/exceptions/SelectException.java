package exceptions;

/**
 * Gestiona excepciones a la hora de buscar entidades.
 *
 * @author Jason.
 */
public class SelectException extends Exception {

    /**
     * Creates a new instance of <code>SelectException</code> without detail
     * message.
     */
    public SelectException() {
    }

    /**
     * Constructs an instance of <code>SelectException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public SelectException(String msg) {
        super(msg);
    }
}
