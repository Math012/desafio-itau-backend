package com.math012.desafio_itau_backend.business.dto.request;

import java.time.OffsetDateTime;

public class TransactionRequestDTOFixture {
    public static TransactionRequestDTO build(Double valor, OffsetDateTime dataHora){
        return new TransactionRequestDTO(valor,dataHora);
    }
}
