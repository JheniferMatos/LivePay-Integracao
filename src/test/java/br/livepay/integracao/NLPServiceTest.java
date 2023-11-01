package br.livepay.integracao;

import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

// import br.livepay.integracao.service.NLPService;
import br.livepay.integracao.service.NLPService2;


@AutoConfigureMockMvc
@SpringBootTest
@DisplayName("Testes para o LiveCommerceController")
public class NLPServiceTest {
    public static void main(String[] args) {
        // Caminho para o modelo treinado
        String modelPath = "src/main/java/br/livepay/integracao/treiner/intent-model.bin";

        NLPService2 nlpService = new NLPService2(modelPath);

        // Exemplo de processamento de texto e obtenção de resposta
        String inputText1 = "Olá, como você está?";
        String inputText2 = "Qual é o preço deste produto?";
        String inputText3 = "Obrigado por sua ajuda.";

        String response1 = nlpService.processText(inputText1);
        String response2 = nlpService.processText(inputText2);
        String response3 = nlpService.processText(inputText3);

        System.out.println("Resposta 1: " + response1);
        System.out.println("Resposta 2: " + response2);
        System.out.println("Resposta 3: " + response3);
    }
}
