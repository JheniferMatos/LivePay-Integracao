package br.livepay.integracao;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import java.util.List;
import java.util.Arrays;
public class NPLServiceReverso {

    private static final String NLP_SERVICE_URL = "http://localhost:8080/api/nlp/intent";

    public static void main(String[] args) throws Exception {
        // Cria um cliente HTTP
        HttpClient httpClient = HttpClientBuilder.create().build();

        // Itera sobre o txt
        for (String line : txt.split("\n")) {
            // Cria uma solicitação POST
            HttpPost request = new HttpPost(NLP_SERVICE_URL);

            // Define o corpo da solicitação como a intenção
            JsonObject json = new JsonObject();
            json.addProperty("phrase", line);
            StringEntity entity = new StringEntity(json.toString());
            request.setEntity(entity);

            // Envia a solicitação e obtém a resposta
            HttpResponse response = httpClient.execute(request);

            // Verifica o código de status da resposta
            if (response.getStatusLine().getStatusCode() != 200) {
                throw new Exception("NLPService retornou um código de status de erro: " + response.getStatusLine().getStatusCode());
            }

            // Obtém o corpo da resposta
            String responseBody = EntityUtils.toString(response.getEntity());

            // Verifica o corpo da resposta
            JsonParser parser = new JsonParser();
            JsonObject responseJson = parser.parse(responseBody).getAsJsonObject();
            if (!responseJson.has("intent")) {
                throw new Exception("O corpo da resposta do NLPService não contém uma intenção");
            }

            // Obtém a intenção da resposta
            String intent = responseJson.get("intent").getAsString();

            // Verifica a intenção
            if (!intents.contains(intent)) {
                throw new Exception("NLPService retornou uma intenção incorreta: " + intent);
            }
        }

        // Sucesso!
        System.out.println("Teste do NLPService passou!");
    }

    private static final List<String> intents = Arrays.asList("intencao_compra", "intencao_info_produto", "intencao_status_pedido", "intencao_info_produto", "intencao_compra", "intencao_compra", "intencao_info_produto", "intencao_status_pedido", "intencao_status_pedido", "intencao_info_produto", "intencao_compra", "intencao_vendas", "intencao_vendas", "intencao_nome", "intencao_pedido", "intencao_quantidade", "intencao_confirmacao_pedido", "intencao_endereco_entrega", "intencao_pagamento");

    private static final String txt = "como faço para comprar um produto\nonde posso encontrar informações sobre um produto\nquero saber o status do meu pedido\ncomo posso verificar o status da entrega\ngostaria de informações sobre os preços dos produtos\ncomo realizar uma compra online\nposso fazer um pedido online\nolá, vi que você está interessado em comprar nosso produto\nsim, eu quero comprar\npor favor, digite seu nome completo\nobrigado joão victor, agora, me informe o pedido que você gostaria\neu quero o fone de ouvido da redragon\nok, colocando fone de ouvido da redragon no carrinho, quantas unidades deseja\neu quero 2 unidades\nok, duas unidades confirmadas, o total é de r$300,00, deseja prosseguir\nsim, confirmo o pedido\nagora me informe seu endereço de entrega rio de janeiro, rj, copacabana, 1243, cep 856749337\nok joão, seu endereço foi registrado com sucesso, agora me informe o meio de pagamento\ndébito";
}


