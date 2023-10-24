package br.livepay.integracao.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.livepay.integracao.model.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {

    List<Transaction> findByTimestampBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<Transaction> findByStatus(String status);

    List<Transaction> findByCustomerId(Long customerId);

    List<Transaction> findByTimestampBetweenAndStatus(LocalDateTime startDate, LocalDateTime endDate, String status);

    List<Transaction> findByPlatformTransactionId(String platformTransactionId);
    
    // Adicione todos os outros métodos de busca necessários para as plataformas de Live e Conversational Commerce.
    // Exemplo de método de busca adicional
    List<Transaction> findByPlatformAndStatus(String platform, String status);


}
