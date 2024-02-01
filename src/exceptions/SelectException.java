package exceptions;

/**
 * Esta excepcion gestiona errores a la hora de crear entidades en el lado
 * cliente.
 * @author Jason.
 */
public class SelectException extends Exception {

    /**
     * Creates a new instance of <code>SelectException</code> without detail message.
     */
    public SelectException() {
    }


    /**
     * Constructs an instance of <code>SelectException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public SelectException(String msg) {
        super(msg);
    }
}
