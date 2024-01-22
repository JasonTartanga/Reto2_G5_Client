/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 * Excepción de error de credenciales cuando se inicia sesión.
 * @author Ian.
 */
public class CredentialErrorException extends Exception {

    /**
     * Creates a new instance of <code>ErrorCredenciales</code> without detail
     * message.
     */
    public CredentialErrorException() {
    }

    /**
     * Constructs an instance of <code>ErrorCredenciales</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public CredentialErrorException(String msg) {
        super(msg);
    }
}
