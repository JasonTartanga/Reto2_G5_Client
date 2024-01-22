/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author poker
 */
public class CredentialErrorException extends Exception {

    /**
     * Creates a new instance of <code>CredentialErrorException</code> without
     * detail message.
     */
    public CredentialErrorException() {
    }

    /**
     * Constructs an instance of <code>CredentialErrorException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public CredentialErrorException(String msg) {
        super(msg);
    }
}
