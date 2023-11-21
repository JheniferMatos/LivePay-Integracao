package br.livepay.integracao.model;

import lombok.Data;

@Data
public class ChatbotResponse {
    private String chatbotResponse;

    public ChatbotResponse(String chatbotResponse) {
        this.chatbotResponse = chatbotResponse;
    }
}