package com.math012.desafio_itau_backend.business.dto.response;

import lombok.*;

import java.time.OffsetDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
public class TransactionResponseDTO {
    private Long id;
    private Double valor;
    private OffsetDateTime dataHora;
}
