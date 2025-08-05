package com.math012.desafio_itau_backend.business;

import com.math012.desafio_itau_backend.business.converter.TransactionConverter;
import com.math012.desafio_itau_backend.business.dto.request.TransactionRequestDTO;
import com.math012.desafio_itau_backend.infra.entity.TransactionEntity;
import com.math012.desafio_itau_backend.infra.exception.RequestInvalidException;
import com.math012.desafio_itau_backend.infra.exception.TransactionDateException;
import com.math012.desafio_itau_backend.infra.exception.TransactionValueException;
import com.math012.desafio_itau_backend.infra.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@AllArgsConstructor
@Service
public class TransactionService {

    private final TransactionConverter converter;

    private final TransactionRepository repository;

    public void saveTransaction(TransactionRequestDTO dto){
        try {
            verifyTransaction(dto);
            TransactionEntity entity = converter.forTransactionEntityFromTransactionRequestDTO(dto);
            entity.setId(1L);
            Instant instant = entity.getDataHora().toInstant();
            entity.setDataHora(instant.atOffset(ZoneOffset.ofHours(-3)));
            repository.saveTransaction(entity);
        }catch (NullPointerException e){
            throw new RequestInvalidException("Erro ao registrar a transação: campos inválidos!");
        }
    }

    public void verifyTransaction(TransactionRequestDTO dto){
        if (!dto.getDataHora().isBefore(OffsetDateTime.now())){
            throw new TransactionDateException("Erro ao registrar a transação: data inválida!");
        }
        if (dto.getValor() < 0){
            throw new TransactionValueException("Erro ao registrar a transação: valor negativo!");
        }
    }

}
