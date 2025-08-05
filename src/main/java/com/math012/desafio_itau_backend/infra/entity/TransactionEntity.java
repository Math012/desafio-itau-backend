package com.math012.desafio_itau_backend.infra.entity;

import lombok.*;

import java.time.OffsetDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
public class TransactionEntity {
    private Long id;
    private Double valor;
    private OffsetDateTime dataHora;
}
