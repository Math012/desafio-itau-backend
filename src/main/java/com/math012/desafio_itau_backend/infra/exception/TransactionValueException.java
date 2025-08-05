package com.math012.desafio_itau_backend.infra.exception;

public class TransactionValueException extends RuntimeException {
    public TransactionValueException(String message) {
        super(message);
    }
}