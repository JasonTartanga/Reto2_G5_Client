package exceptions;

/**
 * Esta excepcion gestiona errores a la hora de crear entidades en el lado
 * cliente.
 * @author Jason.
 */
public class DeleteException extends Exception {

    /**
     * Creates a new instance of <code>DeleteException</code> without detail message.
     */
    public DeleteException() {
    }


    /**
     * Constructs an instance of <code>DeleteException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public DeleteException(String msg) {
        super(msg);
    }
}
