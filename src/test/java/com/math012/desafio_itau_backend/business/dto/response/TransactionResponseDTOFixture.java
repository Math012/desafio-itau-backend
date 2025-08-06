package com.math012.desafio_itau_backend.business.dto.response;

import com.math012.desafio_itau_backend.business.dto.request.TransactionRequestDTO;

import java.time.OffsetDateTime;

public class TransactionResponseDTOFixture {
    public static TransactionResponseDTO build(Long id, Double valor, OffsetDateTime dataHora){
        return new TransactionResponseDTO(id,valor,dataHora);
    }
}
