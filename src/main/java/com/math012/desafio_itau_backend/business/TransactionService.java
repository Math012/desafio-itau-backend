package com.math012.desafio_itau_backend.business;

import com.math012.desafio_itau_backend.business.converter.TransactionConverter;
import com.math012.desafio_itau_backend.business.dto.request.TransactionRequestDTO;
import com.math012.desafio_itau_backend.infra.exception.TransactionDateException;
import com.math012.desafio_itau_backend.infra.exception.TransactionValueException;
import com.math012.desafio_itau_backend.infra.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@AllArgsConstructor
@Service
public class TransactionService {

    private final TransactionConverter converter;

    private final TransactionRepository repository;

    public void verifyTransaction(TransactionRequestDTO dto){
        if (!dto.getDataHora().isBefore(OffsetDateTime.now())){
            throw new TransactionDateException("Erro ao registrar a transação: data inválida!");
        }
        if (dto.getValor() < 0){
            throw new TransactionValueException("Erro ao registrar a transação: valor negativo!");
        }
    }

}
