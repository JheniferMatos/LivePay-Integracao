package br.livepay.integracao.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Defina os prefixos para os tópicos a serem usados
        config.enableSimpleBroker("/topic"); // Tópicos para mensagens a serem enviadas aos clientes
        config.setApplicationDestinationPrefixes("/app"); // Prefixo para mensagens recebidas pelos métodos anotados com @MessageMapping
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Configure o ponto de extremidade para os clientes WebSocket se conectarem
        registry.addEndpoint("/ws").withSockJS(); // O ponto de extremidade WebSocket é '/ws'
    }
}
