package com.math012.desafio_itau_backend.business;

import com.math012.desafio_itau_backend.business.converter.TransactionConverter;
import com.math012.desafio_itau_backend.business.dto.request.TransactionRequestDTO;
import com.math012.desafio_itau_backend.business.dto.request.TransactionRequestDTOFixture;
import com.math012.desafio_itau_backend.infra.entity.TransactionEntity;
import com.math012.desafio_itau_backend.infra.entity.TransactionStatisticsEntity;
import com.math012.desafio_itau_backend.infra.exception.RequestInvalidException;
import com.math012.desafio_itau_backend.infra.exception.TransactionDateException;
import com.math012.desafio_itau_backend.infra.exception.TransactionValueException;
import com.math012.desafio_itau_backend.infra.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @InjectMocks
    TransactionService service;

    @Mock
    TransactionConverter converter;

    @Mock
    TransactionRepository repository;

    TransactionRequestDTO transactionRequestDTO;
    TransactionRequestDTO transactionRequestDTOFutureDate;
    TransactionRequestDTO transactionRequestDTONegativeValue;
    TransactionRequestDTO transactionRequestDTOValueNull;
    TransactionRequestDTO transactionRequestDTODateNull;
    TransactionStatisticsEntity transactionStatistics;
    TransactionStatisticsEntity transactionStatisticsWithEmptyList;
    TransactionEntity transactionEntity1;
    TransactionEntity transactionEntity2;

    List<TransactionEntity> transactionEntityList;
    List<TransactionEntity> transactionEmptyEntityList;

    @BeforeEach
    void setup() {
        transactionRequestDTO = TransactionRequestDTOFixture.build(200.00, OffsetDateTime.parse("2020-08-07T12:34:56.789-03:00"));
        transactionRequestDTOFutureDate = TransactionRequestDTOFixture.build(200.00, OffsetDateTime.parse("2029-08-07T12:34:56.789-03:00"));
        transactionRequestDTOValueNull = TransactionRequestDTOFixture.build(null, OffsetDateTime.parse("2020-08-07T12:34:56.789-03:00"));
        transactionRequestDTODateNull = TransactionRequestDTOFixture.build(200.00, null);
        transactionRequestDTONegativeValue = TransactionRequestDTOFixture.build(-200.00, OffsetDateTime.parse("2020-08-07T12:34:56.789-03:00"));
        transactionEntity1 = new TransactionEntity(1L, 200.00, OffsetDateTime.parse("2020-08-07T12:34:56.789-03:00"));
        transactionEntity2 = new TransactionEntity(2L, 500.00, OffsetDateTime.parse("2020-08-07T12:34:56.789-03:00"));
        transactionEntityList = List.of(transactionEntity1, transactionEntity2);
        transactionStatistics = new TransactionStatisticsEntity(2, 700.0, 350.0, 200.0, 500.0);
        transactionEmptyEntityList = Collections.emptyList();
        transactionStatisticsWithEmptyList = new TransactionStatisticsEntity(0, 0, 0, 0, 0);
    }

    @Test
    void deveSalvarTransacaoComSucesso() {
        when(converter.forTransactionEntityFromTransactionRequestDTO(transactionRequestDTO)).thenReturn(transactionEntity1);
        when(repository.saveTransaction(transactionEntity1)).thenReturn(transactionEntity1);
        service.saveTransaction(transactionRequestDTO);
        verify(converter, times(1)).forTransactionEntityFromTransactionRequestDTO(transactionRequestDTO);
        verify(repository, times(1)).saveTransaction(transactionEntity1);
        verifyNoMoreInteractions(converter);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void deveFalharAoSalvarTransacaoComDataFutura() {
        TransactionDateException transactionDateException = assertThrows(TransactionDateException.class, () -> {
            service.saveTransaction(transactionRequestDTOFutureDate);
        });
        assertEquals("Erro ao registrar a transação: data inválida!", transactionDateException.getMessage());
    }

    @Test
    void deveFalharAoSalvarTransacaoComValorNegativo() {
        TransactionValueException transactionValueException = assertThrows(TransactionValueException.class, () -> {
            service.saveTransaction(transactionRequestDTONegativeValue);
        });
        assertEquals("Erro ao registrar a transação: valor negativo!", transactionValueException.getMessage());
    }

    @Test
    void deveFalharAoSalvarTransacaoComValorNulo() {
        RequestInvalidException requestInvalidException = assertThrows(RequestInvalidException.class, () -> {
            service.saveTransaction(transactionRequestDTOValueNull);
        });
        assertEquals("Erro ao registrar a transação: campos inválidos!", requestInvalidException.getMessage());
    }

    @Test
    void deveFalharAoSalvarTransacaoComDatarNulo() {
        RequestInvalidException requestInvalidException = assertThrows(RequestInvalidException.class, () -> {
            service.saveTransaction(transactionRequestDTODateNull);
        });
        assertEquals("Erro ao registrar a transação: campos inválidos!", requestInvalidException.getMessage());
    }

    @Test
    void deveRetornarEstatisticasDasTransacoes() {
        when(repository.findByTime()).thenReturn(transactionEntityList);
        TransactionStatisticsEntity response = service.transactionStatistics();
        assertEquals(transactionStatistics, response);
    }

    @Test
    void deveRetornarEstatisticasZeradasDasTransacoes() {
        when(repository.findByTime()).thenReturn(transactionEmptyEntityList);
        TransactionStatisticsEntity response = service.transactionStatistics();
        assertEquals(transactionStatisticsWithEmptyList, response);
    }

    @Test
    void deveDeletarTodasAsTransacoesComSucesso() {
        doNothing().when(repository).deleteAll();
        service.deleteAll();
        verify(repository, times(1)).deleteAll();
        verifyNoMoreInteractions(repository);
    }

    @Test
    void performanceTestSaveTransaction() {
        Long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            when(converter.forTransactionEntityFromTransactionRequestDTO(transactionRequestDTO)).thenReturn(transactionEntity1);
            when(repository.saveTransaction(transactionEntity1)).thenReturn(transactionEntity1);
            service.saveTransaction(transactionRequestDTO);
        }
        Long end = System.currentTimeMillis();
        Long total = end - start;
        assertThat(total).isLessThan(255);
    }

    @Test
    void performanceTestTransactionStatistics() {
        Long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            when(repository.findByTime()).thenReturn(transactionEntityList);
            service.transactionStatistics();
        }
        Long end = System.currentTimeMillis();
        Long total = end - start;
        assertThat(total).isLessThan(255);
    }

    @Test
    void performanceTestDeleteAll() {
        Long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            doNothing().when(repository).deleteAll();
            service.deleteAll();
        }
        Long end = System.currentTimeMillis();
        Long total = end - start;
        assertThat(total).isLessThan(255);
    }
}