package com.math012.desafio_itau_backend.business.converter;

import com.math012.desafio_itau_backend.business.dto.request.TransactionRequestDTO;
import com.math012.desafio_itau_backend.business.dto.response.TransactionResponseDTO;
import com.math012.desafio_itau_backend.infra.entity.TransactionEntity;
import org.springframework.stereotype.Component;

@Component
public class TransactionConverter {
    public TransactionEntity forTransactionEntityFromTransactionRequestDTO(TransactionRequestDTO transactionRequestDTO){
        return TransactionEntity.builder()
                .valor(transactionRequestDTO.getValor())
                .dataHora(transactionRequestDTO.getDataHora())
                .build();
    }

    public TransactionResponseDTO forTransactionResponseDTOFromTransactionEntity(TransactionEntity transactionEntity){
        return TransactionResponseDTO.builder()
                .id(transactionEntity.getId())
                .valor(transactionEntity.getValor())
                .dataHora(transactionEntity.getDataHora())
                .build();
    }
}