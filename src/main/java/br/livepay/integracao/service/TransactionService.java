package br.livepay.integracao.service;

import java.time.LocalDateTime;
import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.livepay.integracao.model.Transaction;
import br.livepay.integracao.repository.TransactionRepository;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }
    
    //lógica para criar uma transação e salvá-la no banco de dados
    public Transaction createTransaction(Transaction transaction) {
        
        transaction.setTimestamp(LocalDateTime.now());
        return transactionRepository.save(transaction);
    }
    
    // buscar transações por customerId
    public List<Transaction> getTransactionsByCustomerId(Long customerId) {
    
        return transactionRepository.findByCustomerId(customerId);
    }

    // buscar transações por intervalo de datas
    public List<Transaction> getTransactionsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
    
        return transactionRepository.findByTimestampBetween(startDate, endDate);
    }
    // buscar transações por status
    public List<Transaction> getTransactionsByStatus(String status) {
    
        return transactionRepository.findByStatus(status);
    }
    
    // buscar transações por intervalo de datas e status
    public List<Transaction> getTransactionsByDateRangeAndStatus(LocalDateTime startDate, LocalDateTime endDate, String status) {
    
        return transactionRepository.findByTimestampBetweenAndStatus(startDate, endDate, status);
    }


}

