package br.livepay.integracao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import br.livepay.integracao.model.ChatBot;

@Repository
public interface ChatBotRepository extends JpaRepository<ChatBot, Long> {
    
}
