package com.math012.desafio_itau_backend.infra.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
public class TransactionStatisticsEntity {
    private long count;
    private double sum;
    private double avg;
    private double min;
    private double max;
}