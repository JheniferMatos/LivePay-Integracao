package br.livepay.integracao.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;

@Service
public class NLPService2 {
    private DoccatModel model;
    private DocumentCategorizerME categorizer;

    public NLPService2(@Value("${nlp.modelPath}") String modelPath) {
        // Carregue o modelo treinado
        try {
            model = new DoccatModel(new FileInputStream(modelPath)); // Use FileInputStream
            categorizer = new DocumentCategorizerME(model);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String processText(String text) {
        // Pré-processamento do texto (se necessário)

        // Classifique o texto com base nas intenções
        double[] outcomes = categorizer.categorize(new String[]{text});

        // Obtenha a categoria com a maior probabilidade
        String bestCategory = categorizer.getBestCategory(outcomes);

        // Aqui, você pode mapear a categoria para uma resposta específica
        String response = getResponseForCategory(bestCategory);

        return response;
    }

    private String getResponseForCategory(String category) {
        // Implemente a lógica para gerar respostas com base nas categorias/intenções
        // Por exemplo, usando um mapa de categorias para respostas

        // Exemplo simples:
        Map<String, String> categoryResponses = new HashMap<>();
        categoryResponses.put("saudacao", "Olá! Como posso ajudar?");
        categoryResponses.put("pedido_informacao", "Claro, vou te ajudar com isso.");
        categoryResponses.put("despedida", "Até logo! Se precisar de mais ajuda, é só perguntar.");

        return categoryResponses.getOrDefault(category, "Desculpe, não entendi. Pode reformular sua pergunta?");
    }
}
