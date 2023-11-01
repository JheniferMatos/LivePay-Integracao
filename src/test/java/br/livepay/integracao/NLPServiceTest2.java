package br.livepay.integracao;

import org.junit.jupiter.api.Test;
import br.livepay.integracao.service.NLPService;

import static org.junit.jupiter.api.Assertions.*;

class NLPServiceTest2 {

    @Test
void getIntent_com_texto_vazio() {
    
    // Arrange
    NLPService service = new NLPService();
    String text = "";

    // Act
    String intent = service.getIntent(text);

    // Assert
    assertEquals("Desculpe, não consigo entender a intenção.", intent);
}

    @Test
    void getIntent_com_texto_simples() {
        // Arrange
        NLPService service = new NLPService();
        String text = "Olá";

        // Act
        String intent = service.getIntent(text);

        // Assert
        assertEquals("intencao_oi", intent);
    }

    @Test
    void getIntent_com_texto_completo() {
        // Arrange
        NLPService service = new NLPService();
        String text = "como realizar uma compra online";

        // Act
        String intent = service.getIntent(text);

        // Assert
        assertEquals("intencao_compra", intent);
    }

    @Test
    void respondToIntent_com_intent_compra() {
        // Arrange
        NLPService service = new NLPService();
        String intent = "intencao_compra";
        String userResponse = "João da Silva";

        // Act
        String response = service.respondToIntent(intent, userResponse);

        // Assert
        assertEquals("Por favor, digite seu nome completo", response);
    }

    @Test
    void respondToIntent_com_intent_confirmacao_pedido() {
        // Arrange
        NLPService service = new NLPService();
        String intent = "intencao_confirmacao_pedido";
        String userResponse = "Sim";

        // Act
        String response = service.respondToIntent(intent, userResponse);

        // Assert
        assertEquals("Agora me informe seu endereço de entrega:", response);
    }

    @Test
    void respondToIntent_com_intent_pagamento() {
        // Arrange
        NLPService service = new NLPService();
        String intent = "intencao_pagamento";
        String userResponse = "Débito";

        // Act
        String response = service.respondToIntent(intent, userResponse);

        // Assert
        assertEquals("Insira o número do cartão de débito:", response);
    }

    @Test
    void respondToIntent_com_intent_nao_encontrado() {
        // Arrange
        NLPService service = new NLPService();
        String intent = "intencao_desconhecida";
        String userResponse = "Qualquer coisa";

        // Act
        String response = service.respondToIntent(intent, userResponse);

        // Assert
        assertEquals("Desculpe, não entendi. Como posso ajudá-lo?", response);
    }
}
