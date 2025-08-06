package com.math012.desafio_itau_backend.infra.repository;

import com.math012.desafio_itau_backend.infra.entity.TransactionEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository {

    TransactionEntity saveTransaction(TransactionEntity transactionEntity);

    void deleteAll();

    List<TransactionEntity> findByTime();

    List<TransactionEntity> findByTimeAdapter(Long time);
}