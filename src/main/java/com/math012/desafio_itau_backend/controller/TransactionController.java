package com.math012.desafio_itau_backend.controller;

import com.math012.desafio_itau_backend.business.TransactionService;
import com.math012.desafio_itau_backend.business.dto.request.TransactionRequestDTO;
import com.math012.desafio_itau_backend.infra.entity.TransactionStatisticsEntity;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/transacao")
    public ResponseEntity<Void> deleteAll(){
        service.deleteAll();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/estatistica")
    public ResponseEntity<TransactionStatisticsEntity> transactionStatistics(){
        return ResponseEntity.ok(service.transactionStatistics());
    }
}