package br.livepay.integracao.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Transaction {
    @Id
    private String id;
    private String customerId;
    private double amount;
    private LocalDateTime timestamp;
    
}
