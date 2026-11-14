package com.felipe.bankcore.exception;

/**
 * Representa uma exceção lançada quando o valor de uma operação é menor ou igual a zero.
 *
 */
public class InvalidAmountException extends RuntimeException {
    /**
     *
     * @param message a mensagem explicando o erro.
     */
    public InvalidAmountException(String message) {
        super(message);
    }
}
