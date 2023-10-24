package br.livepay.integracao.controller;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.livepay.integracao.model.Transaction;
import br.livepay.integracao.service.TransactionService;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
    
    @PostMapping
    public Transaction createTransaction(@RequestBody Transaction transaction) {
        // Chame o serviço para criar uma nova transação
        return transactionService.createTransaction(transaction);
    }
    
    // Implemente outros métodos conforme necessário, como atualizar transação ou buscar transações por customerId.
}
