package com.math012.desafio_itau_backend.controller;

import com.math012.desafio_itau_backend.business.TransactionService;
import com.math012.desafio_itau_backend.business.dto.request.TransactionRequestDTO;
import com.math012.desafio_itau_backend.infra.entity.TransactionStatisticsEntity;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "TransactionController", description = "Endpoint para cadastrar, deletar e consultar estatísticas de transações")
@AllArgsConstructor
@RestController
@RequestMapping()
public class TransactionController {

    private final TransactionService service;

    @PostMapping("/transacao")
    @Operation(summary = "Cadastro de transação", description = "Cadastra uma transação recebendo em seu body um TransactionRequestDTO.")
    @ApiResponse(responseCode = "201", description = "Transação cadastrada com sucesso.")
    @ApiResponse(responseCode = "400", description = "Transação não salva, campos inválidos.")
    @ApiResponse(responseCode = "422", description = "Transação não salva, data inválida.")
    @ApiResponse(responseCode = "422", description = "Transação não salva, valor inválido.")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<Void> saveTransaction(@RequestBody TransactionRequestDTO request){
        service.saveTransaction(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/transacao")
    @Operation(summary = "Deleta todas as transações", description = "Realiza a deleção de todas as transações registradas.")
    @ApiResponse(responseCode = "200", description = "Transações deletadas com sucesso.")
    @ApiResponse(responseCode = "500", description = "Erro no servidor.")
    public ResponseEntity<Void> deleteAll(){
        service.deleteAll();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/estatistica")
    @Operation(summary = "Retorna as estatísticas das transações que aconteceram nos últimos 60 segundos", description = "Retorna as estatísticas das transações que aconteceram nos últimos 60 segundos.")
    @ApiResponse(responseCode = "200", description = "Estatísticas retornadas com sucesso.")
    @ApiResponse(responseCode = "500", description = "Erro no servidor.")
    public ResponseEntity<TransactionStatisticsEntity> transactionStatistics(){
        return ResponseEntity.ok(service.transactionStatistics());
    }

    @GetMapping("/estatistica/{time}")
    @Operation(summary = "Retorna as estatísticas das transações com base no tempo informado pelo usuário.", description = "Retorna as estatísticas das transações que aconteceram com base no tempo fornecido pelo usuário através do path variable.")
    @ApiResponse(responseCode = "200", description = "Estatísticas retornadas com sucesso.")
    @ApiResponse(responseCode = "500", description = "Erro no servidor.")
    public ResponseEntity<TransactionStatisticsEntity> transactionStatistics(@PathVariable("time") Long time){
        return ResponseEntity.ok(service.transactionStatisticsAdapter(time));
    }
}