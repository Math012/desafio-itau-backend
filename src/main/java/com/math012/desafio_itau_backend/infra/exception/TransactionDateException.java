package com.math012.desafio_itau_backend.infra.exception;

public class TransactionDateException extends RuntimeException {
    public TransactionDateException(String message) {
        super(message);
    }
}