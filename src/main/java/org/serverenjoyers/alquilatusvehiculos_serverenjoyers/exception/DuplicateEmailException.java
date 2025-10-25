package org.serverenjoyers.alquilatusvehiculos_serverenjoyers.exception;

public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException(String message){
        super(message);
    }
}
