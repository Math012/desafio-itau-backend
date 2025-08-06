package com.math012.desafio_itau_backend.business;

import com.math012.desafio_itau_backend.business.converter.TransactionConverter;
import com.math012.desafio_itau_backend.business.dto.request.TransactionRequestDTO;
import com.math012.desafio_itau_backend.infra.entity.TransactionEntity;
import com.math012.desafio_itau_backend.infra.entity.TransactionStatisticsEntity;
import com.math012.desafio_itau_backend.infra.exception.RequestInvalidException;
import com.math012.desafio_itau_backend.infra.exception.TransactionDateException;
import com.math012.desafio_itau_backend.infra.exception.TransactionValueException;
import com.math012.desafio_itau_backend.infra.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.DoubleSummaryStatistics;
import java.util.stream.Collectors;


@AllArgsConstructor
@Service
public class TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    private final TransactionConverter converter;

    private final TransactionRepository repository;

    public void saveTransaction(TransactionRequestDTO dto) {
        try {
            logger.info("saveTransaction: Validando os campos de TransactionRequestDTO");
            verifyTransaction(dto);
            logger.info("saveTransaction: Convertendo de TransactionRequestDTO para TransactionEntity");
            TransactionEntity entity = converter.forTransactionEntityFromTransactionRequestDTO(dto);
            entity.setId(1L);
            Instant instant = entity.getDataHora().toInstant();
            entity.setDataHora(instant.atOffset(ZoneOffset.ofHours(-3)));
            logger.info("saveTransaction: salvando a entidade");
            repository.saveTransaction(entity);
        } catch (NullPointerException e) {
            throw new RequestInvalidException("Erro ao registrar a transação: campos inválidos!");
        }
    }

    public void deleteAll() {
        logger.info("deleteAll: deletando todas as transações");
        repository.deleteAll();
    }

    public TransactionStatisticsEntity transactionStatistics() {
        logger.info("transactionStatistics: recebendo a lista de transações por periado");
        var listTransactionsMadeInLastMinute = repository.findByTime();
        DoubleSummaryStatistics dSS = listTransactionsMadeInLastMinute.stream().collect(Collectors.summarizingDouble(TransactionEntity::getValor));
        if (!listTransactionsMadeInLastMinute.isEmpty()) {
            logger.info("transactionStatistics: Lista encontrada calculando as estatísticas das transações");
            return new TransactionStatisticsEntity(dSS.getCount(), dSS.getSum(), dSS.getAverage(), dSS.getMin(), dSS.getMax());
        } else {
            logger.info("transactionStatistics: Lista não encontrada, definindo as estatísticas para o valor 0");
            return new TransactionStatisticsEntity(0, 0, 0, 0, 0);
        }
    }

    public TransactionStatisticsEntity transactionStatisticsAdapter(Long time) {
        logger.info("transactionStatisticsAdapter: recebendo a lista de transações por periado");
        var listTransactionsMadeInLastMinute = repository.findByTimeAdapter(time);
        DoubleSummaryStatistics dSS = listTransactionsMadeInLastMinute.stream().collect(Collectors.summarizingDouble(TransactionEntity::getValor));
        if (!listTransactionsMadeInLastMinute.isEmpty()) {
            logger.info("transactionStatisticsAdapter: Lista encontrada calculando as estatísticas das transações");
            return new TransactionStatisticsEntity(dSS.getCount(), dSS.getSum(), dSS.getAverage(), dSS.getMin(), dSS.getMax());
        } else {
            logger.info("transactionStatisticsAdapter: Lista não encontrada, definindo as estatísticas para o valor 0");
            return new TransactionStatisticsEntity(0, 0, 0, 0, 0);
        }
    }

    public void verifyTransaction(TransactionRequestDTO dto) {
        if (!dto.getDataHora().isBefore(OffsetDateTime.now())) {
            logger.info("verifyTransaction: Erro encontrado ao tentar salva a transação com data inválida");
            throw new TransactionDateException("Erro ao registrar a transação: data inválida!");
        }
        if (dto.getValor() < 0) {
            logger.info("verifyTransaction: Erro encontrado ao tentar salva a transação com valor inválido");
            throw new TransactionValueException("Erro ao registrar a transação: valor negativo!");
        }
    }
}