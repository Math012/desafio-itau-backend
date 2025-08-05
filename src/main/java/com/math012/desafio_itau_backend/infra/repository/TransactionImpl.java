package com.math012.desafio_itau_backend.infra.repository;

import com.math012.desafio_itau_backend.infra.entity.TransactionEntity;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class TransactionImpl implements TransactionRepository{

    List<TransactionEntity> allTransactions = new ArrayList<>();

    @Override
    public TransactionEntity saveTransaction(TransactionEntity transactionEntity) {
        allTransactions.add(transactionEntity);
        return transactionEntity;
    }

    @Override
    public void deleteAll() {
        allTransactions.clear();
    }

    @Override
    public List<TransactionEntity> findByTime() {
        List<TransactionEntity> transactionsSelectedByPeriod = new ArrayList<>();
        OffsetDateTime timeStart = OffsetDateTime.now().minusSeconds(60L);
        OffsetDateTime timeEnd = OffsetDateTime.now();
        for (TransactionEntity entity:allTransactions){
            if (entity.getDataHora().isAfter(timeStart) || entity.getDataHora().equals(timeStart) && entity.getDataHora().isBefore(timeEnd) || entity.getDataHora().equals(timeEnd)){
                transactionsSelectedByPeriod.add(entity);
            }
        }
        return transactionsSelectedByPeriod;
    }
}