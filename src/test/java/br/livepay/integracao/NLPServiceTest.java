package br.livepay.integracao;

import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import br.livepay.integracao.service.NLPService;


@AutoConfigureMockMvc
@SpringBootTest
@DisplayName("Testes para o LiveCommerceController")
public class NLPServiceTest {

    public static void main(String[] args) throws IOException {
        // Cria um conjunto de dados de teste
        String[] inputs = {
            "quero comprar um celular",
            "quero saber o status do meu pedido",
            "quero cancelar um pedido",
            "quero devolver um produto",
            "posso fazer um pedido online"
        };
        String[] expectedOutputs = {
            "intencao_compra",
            "intencao_status_pedido",
            "intencao_cancelamento",
            "intencao_devolucao",
            "intencao_compra"
        };

        // Cria um objeto NLPService
        NLPService nlpService = new NLPService();

        // Testa o arquivo
        int correct = 0;
        int total = inputs.length;
        for (int i = 0; i < total; i++) {
            String intent = nlpService.getIntent(inputs[i]);
            if (intent.equals(expectedOutputs[i])) {
                correct++;
            }
        }

        // Avalia os resultados
        double precision = (double) correct / total;
        System.out.println("Precisão: " + precision);
    }
}