package br.livepay.integracao.service;

import opennlp.tools.doccat.DoccatModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.livepay.integracao.model.Produto;
import br.livepay.integracao.repository.ProdutoRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChatBotService {

    private final Logger logger = LoggerFactory.getLogger(ChatBotService.class);

    private final ProdutoRepository produtoRepository;
    private final OpenNLP openNLP;

    private List<Produto> listaDeProdutos;

    @Autowired
    public ChatBotService(ProdutoRepository produtoRepository, OpenNLP openNLP) {
        this.produtoRepository = produtoRepository;
        this.openNLP = openNLP;
        this.listaDeProdutos = produtoRepository.findAll();
    }

    public String processarPerguntaUsuario(String userInput) throws Exception {
        // Treine o modelo de categorização com os dados de treinamento
        DoccatModel model = openNLP.trainCategorizerModel("categorizer.txt");

        // Carregue pares de pergunta-resposta do arquivo
        Map<String, String> questionAnswer = loadQuestionAnswerPairs("questions.txt");

        // Quebrar a entrada do usuário em sentenças usando detecção de sentença
        String[] sentences = openNLP.breakSentences(userInput, "models/pt-sent.bin");

        // Inicializar a resposta fora do loop
        StringBuilder answer = new StringBuilder();
        boolean conversaCompleta = false;

        for (String sentence : sentences) {
            String[] tokens = openNLP.tokenizeSentence(sentence, "models/pt-token.bin");
            String[] posTags = openNLP.detectPOSTags(tokens, "models/pt-pos-maxent.bin");
            String[] lemmas = openNLP.lemmatizeTokens(tokens, posTags, "models/lemmatizer.bin");

            String category = openNLP.detectCategory(model, lemmas);

            if (category.equals("fazer-pedido")) {
                // Extrai o nome do produto da pergunta do usuário
                String produtoNome = extrairNomeProduto(userInput);

                // Verifica se o produto foi corretamente extraído
                if (produtoNome != null && !produtoNome.isEmpty()) {
                    // Chama o método para fazer o pedido
                    String respostaPedido = fazerPedido(produtoNome, 1); // 1 é a quantidade, você pode ajustar conforme
                                                                         // necessário

                    // Adiciona a resposta ao StringBuilder 'answer'
                    answer.append(respostaPedido);
                } else {
                    // Se o nome do produto não puder ser extraído corretamente, retorna uma
                    // mensagem de erro
                    answer.append(
                            "Desculpe, não foi possível identificar o produto. Por favor, verifique o nome do produto e tente novamente.");
                }
            } else if (category.equals("consulta-produto")) {
                // Colete todos os nomes dos produtos da lista de produtos
                List<String> listaDeNomesDeProdutos = new ArrayList<>();
                for (Produto produto : listaDeProdutos) {
                    listaDeNomesDeProdutos.add(produto.getName());
                }

                // Substitua "[lista_produto]" pela lista real de produtos
                String listaProdutoText = questionAnswer.get(category);
                answer.append("- Esta e a nossa lista de produtos:");
                answer.append("\n");
                for (String nomeDeProduto : listaDeNomesDeProdutos) {
                    answer.append("    * ").append(nomeDeProduto).append("\n");
                }
            } else {
                answer.append(" ").append(questionAnswer.get(category));

                if (category.equals("conversation-complete")) {
                    System.out.println("Chatbot: Conversa completa. Até logo!");
                    conversaCompleta = true;
                    break;
                }
            }
        }

        return conversaCompleta ? "Chatbot: Conversa completa. Até logo!" : answer.toString();

    }

    private Map<String, String> loadQuestionAnswerPairs(String filePath) throws Exception {
        Map<String, String> questionAnswer = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isEmpty())
                    break;
                String[] categoryQuestion = line.split(";");
                questionAnswer.put(categoryQuestion[0], categoryQuestion[1]);
            }
        }

        return questionAnswer;
    }

    public String buscarListaProdutos() {
        StringBuilder resposta = new StringBuilder("Nossos produtos incluem:\n");

        for (Produto produto : listaDeProdutos) {
            resposta.append("- ").append(produto.getName()).append("\n");
        }

        return resposta.toString();
    }

    public String extrairNomeProduto(String userInput) {
        // Padrões para identificar o início e fim do nome do produto
        String inicioPadrao = "fazer pedido do produto";
        String fimPadrao = "?";
    
        int inicioIndex = userInput.indexOf(inicioPadrao);
        int fimIndex = userInput.indexOf(fimPadrao);
    
        if (inicioIndex != -1 && fimIndex != -1) {
            // Extrai o nome do produto entre o início e o fim
            String produtoNome = userInput.substring(inicioIndex + inicioPadrao.length(), fimIndex).trim();
            return produtoNome;
        } else {
            // Se não encontrar os padrões, retorna null
            return null;
        }
    }

    public String fazerPedido(String produtoNome, int quantidade) {
        // Verifica se o produto existe na lista de produtos
        Produto produtoSelecionado = null;
        for (Produto produto : listaDeProdutos) {
            if (produto.getName().equalsIgnoreCase(produtoNome)) {
                produtoSelecionado = produto;
                break;
            }
        }

        // Se o produto não for encontrado, retorna uma mensagem de erro
        if (produtoSelecionado == null) {
            return "Produto não encontrado. Por favor, verifique o nome do produto e tente novamente.";
        }

        // Calcula o total do pedido
        double total = produtoSelecionado.getPrice() * quantidade;

        // Cria a mensagem de confirmação do pedido
        String mensagem = "Pedido confirmado:\n" +
                "Produto: " + produtoSelecionado.getName() + "\n" +
                "Quantidade: " + quantidade + "\n" +
                "Preço unitário: R$" + produtoSelecionado.getPrice() + "\n" +
                "Total do pedido: R$" + total;

        // Substitui os marcadores de posição na mensagem
        mensagem = mensagem.replace("[nome_produto]", produtoSelecionado.getName());
        mensagem = mensagem.replace("[price]", String.valueOf(total));

        return mensagem;
    }

}