package com.math012.desafio_itau_backend.business.dto.request;

import lombok.*;

import java.time.OffsetDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
public class TransactionRequestDTO {
    private Double valor;
    private OffsetDateTime dataHora;
}