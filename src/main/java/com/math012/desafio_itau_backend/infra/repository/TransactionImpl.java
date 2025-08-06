package com.math012.desafio_itau_backend.infra.repository;

import com.math012.desafio_itau_backend.infra.entity.TransactionEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class TransactionImpl implements TransactionRepository {

    private static final Logger logger = LoggerFactory.getLogger(TransactionImpl.class);

    List<TransactionEntity> allTransactions = new ArrayList<>();

    @Override
    public TransactionEntity saveTransaction(TransactionEntity transactionEntity) {
        logger.info("repository: Salvando a transação");
        allTransactions.add(transactionEntity);
        logger.info("repository: transação salva com sucesso");
        return transactionEntity;
    }

    @Override
    public void deleteAll() {
        logger.info("repository: deletando todas as transações");
        allTransactions.clear();
        logger.info("repository: transações deletadas com sucesso");
    }

    @Override
    public List<TransactionEntity> findByTime() {
        List<TransactionEntity> transactionsSelectedByPeriod = new ArrayList<>();
        logger.info("repository: Definindo hora de inicio da transação");
        OffsetDateTime timeStart = OffsetDateTime.now().minusSeconds(60L);
        logger.info("repository: Definindo hora final da transação");
        OffsetDateTime timeEnd = OffsetDateTime.now();
        logger.info("repository: Buscando transações dentro do periado");
        for (TransactionEntity entity : allTransactions) {
            if (entity.getDataHora().isAfter(timeStart) || entity.getDataHora().equals(timeStart) && entity.getDataHora().isBefore(timeEnd) || entity.getDataHora().equals(timeEnd)) {
                logger.info("repository: Transação encontrada");
                transactionsSelectedByPeriod.add(entity);
                logger.info("repository: Transações adicionadas a lista de transações por periado");
            }
        }
        logger.info("repository: Retornando a lista de transações");
        return transactionsSelectedByPeriod;
    }
}