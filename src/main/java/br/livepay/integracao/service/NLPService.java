package br.livepay.integracao.service;

import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
// import opennlp.tools.util.InputStreamFactory;
// import opennlp.tools.util.ObjectStream;
// import opennlp.tools.util.PlainTextByLineStream;
// import opennlp.tools.util.TrainingParameters;
// import opennlp.tools.util.model.BaseModel;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
// import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;

@Service
public class NLPService {

    private DocumentCategorizerME categorizer;


public NLPService() {
    // Carrega o modelo pré-treinado para classificação de intenções
    try (InputStream modelIn = getClass().getResourceAsStream("/br/livepay/integracao/treiner/intent-model.bin")) {
        DoccatModel model = new DoccatModel(modelIn);
        categorizer = new DocumentCategorizerME(model);
    } catch (IOException e) {
        e.printStackTrace();
    }
    
}

public String getIntent(String text) {
    NLPService teste = new NLPService();
    if (categorizer == null) {
        return "Desculpe, não consigo entender a intenção.";
    }

    // Limpa o texto de entrada
    String normalizedText = StringUtils.normalizeSpace(text);

    // Pré-processamento do texto
    normalizedText = normalizedText.toLowerCase(); // Converte o texto para minúsculas
    normalizedText = normalizedText.replaceAll("[^a-zA-Z0-9  ]", ""); // Remove a pontuação

    String[] tokens = normalizedText.split(" ");
    double[] outcomes = categorizer.categorize(tokens);
    String intent = categorizer.getBestCategory(outcomes);

    return intent;
}

    public String respondToIntent(String intent, String userResponse) {
        NLPService teste = new NLPService();
        if (intent.equals("intencao_compra")) {
            return "Por favor, digite seu nome completo";
        }
        else if (intent.equals("intencao_nome")) {
            return "Obrigado " + userResponse + ", agora, me informe o pedido que gostaria.";
        } else if (intent.equals("intencao_pedido")) {
            return "Ok, colocando " + userResponse + " no carrinho, quantas unidades deseja?";
        } else if (intent.equals("intencao_quantidade")) {
            return "Ok, " + userResponse + " unidades confirmadas, o Total é de R$300,00, deseja prosseguir?";
        } else if (intent.equals("intencao_confirmacao_pedido")) {
            
                return "Agora me informe seu endereço de entrega:";
            } 
        else if (intent.equals("intencao_endereco_entrega")) {
            return "Ok " + userResponse + ", seu endereço foi registrado com sucesso, agora me informe o meio de pagamento.";
        } else if (intent.equals("intencao_pagamento")) {
            if (userResponse.contains("Débito")) {
                return "Insira o número do cartão de débito:";
            } else {
                return "Desculpe, só aceitamos pagamento por débito. Por favor, insira o número do cartão de débito:";
            }
        } else if (intent.equals("intencao_numero_cartao")) {
            return "Agora insira a data de vencimento:";
        } else if (intent.equals("intencao_data_vencimento")) {
            return "Agora envie o CVV que consta no cartão:";
        } else if (intent.equals("intencao_cvv")) {
            return "Digite o nome que consta no cartão:";
        } else if (intent.equals("intencao_nome_cartao")) {
            return "Por favor, digite o CPF do dono do cartão:";
        } else if (intent.equals("intencao_cpf")) {
            return "Obrigado, seu pedido foi realizado com sucesso e logo será enviado para a transportadora.";
        } else {
            return "Desculpe, não entendi. Como posso ajudá-lo?";
        }
        
        }}
