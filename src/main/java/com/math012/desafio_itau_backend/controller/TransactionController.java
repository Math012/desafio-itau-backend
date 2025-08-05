package com.math012.desafio_itau_backend.controller;

import com.math012.desafio_itau_backend.business.TransactionService;
import com.math012.desafio_itau_backend.business.dto.request.TransactionRequestDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping()
public class TransactionController {

    private final TransactionService service;

    @PostMapping("/transacao")
    public ResponseEntity<Void> saveTransaction(@RequestBody TransactionRequestDTO request){
        service.saveTransaction(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
