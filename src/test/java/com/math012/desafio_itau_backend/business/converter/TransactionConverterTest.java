package com.math012.desafio_itau_backend.business.converter;

import com.math012.desafio_itau_backend.business.dto.request.TransactionRequestDTO;
import com.math012.desafio_itau_backend.business.dto.request.TransactionRequestDTOFixture;
import com.math012.desafio_itau_backend.business.dto.response.TransactionResponseDTO;
import com.math012.desafio_itau_backend.business.dto.response.TransactionResponseDTOFixture;
import com.math012.desafio_itau_backend.infra.entity.TransactionEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TransactionConverterTest {
    @InjectMocks
    TransactionConverter converter;

    TransactionEntity transactionEntity;
    TransactionRequestDTO transactionRequestDTO;
    TransactionResponseDTO transactionResponseDTO;

    @BeforeEach
    void setup() {
        transactionEntity = new TransactionEntity(1L, 200.00, OffsetDateTime.parse("2020-08-07T12:34:56.789-03:00"));
        transactionRequestDTO = TransactionRequestDTOFixture.build(200.00, OffsetDateTime.parse("2020-08-07T12:34:56.789-03:00"));
        transactionResponseDTO = TransactionResponseDTOFixture.build(1L, 200.00, OffsetDateTime.parse("2020-08-07T12:34:56.789-03:00"));
    }

    @Test
    void deveConverterParaTransactionEntityComSucesso() {
        TransactionEntity response = converter.forTransactionEntityFromTransactionRequestDTO(transactionRequestDTO);
        response.setId(1L);
        assertEquals(transactionEntity, response);
    }

    @Test
    void deveConverterParaTransactionResponseDTOComSucesso() {
        TransactionResponseDTO response = converter.forTransactionResponseDTOFromTransactionEntity(transactionEntity);
        response.setId(1L);
        assertEquals(transactionResponseDTO, response);
    }
}