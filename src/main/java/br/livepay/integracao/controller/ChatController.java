package br.livepay.integracao.controller;

import br.livepay.integracao.model.ChatbotRequest;
import br.livepay.integracao.model.ChatbotResponse;
import br.livepay.integracao.model.Produto;
import br.livepay.integracao.service.ChatBotService;
// import br.livepay.integracao.service.ChatService;
import br.livepay.integracao.service.ProdutoService;
import jakarta.persistence.PostLoad;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatBotService chatBotService;

    @PostMapping("/send")
    public String processarEntrada(@RequestBody String userInput) {
        try {
            return chatBotService.processarPerguntaUsuario(userInput);
        } catch (Exception e) {
            // Handle exceptions appropriately
            return "Erro ao processar a entrada do usuário.";
        }
    }


    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/produtos")
    public List<Produto> obterProdutos() {
        return produtoService.obterTodosProdutos();
    }
}