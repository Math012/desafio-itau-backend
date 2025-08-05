package com.math012.desafio_itau_backend.infra.exception.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StructException {
    private Date timestamp;
    private String msg;
    private String detail;
}