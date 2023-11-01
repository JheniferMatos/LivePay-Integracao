package br.livepay.integracao.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.messaging.handler.annotation.MessageMapping;
// import org.springframework.messaging.handler.annotation.SendTo;
// import org.springframework.messaging.handler.annotation.SendTo;
// import org.springframework.messaging.simp.SimpMessagingTemplate;
// import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.livepay.integracao.model.ChatBot;
import br.livepay.integracao.service.ChatBotService;
// import br.livepay.integracao.service.NLPService;

@RestController
@RequestMapping("/chat")
public class ChatBotController {

    private final ChatBotService chatBotService;

    @Autowired
    public ChatBotController(ChatBotService chatBotService) {
        this.chatBotService = chatBotService;
    }

    @PostMapping("/send")
    public ChatBot sendMessage(@RequestBody Map<String, String> body) {
        String content = body.get("content");
        String sender = body.get("sender");
        return chatBotService.sendMessage(content, sender);
    }

    @PostMapping("/join")
    public ChatBot joinChat(@RequestBody String sender) {
        return chatBotService.joinChat(sender);
    }

    @PostMapping("/leave")
    public ChatBot leaveChat(@RequestBody String sender) {
        return chatBotService.leaveChat(sender);
    }
}

// private final ChatBotService chatBotService;
// private final NLPService nlpService;
// private final SimpMessagingTemplate messagingTemplate;

// public ChatBotController(ChatBotService chatBotService, NLPService
// nlpService,
// SimpMessagingTemplate messagingTemplate) {
// this.chatBotService = chatBotService;
// this.nlpService = nlpService;
// this.messagingTemplate = messagingTemplate;
// }

// // @MessageMapping("/chatbot")
// // @SendTo("/topic/chat")
// @PostMapping
// public String chat(@RequestBody ChatBot message){
// // Trate a mensagem recebida, processe-a e responda conforme necessário
// // Aqui você pode integrar o serviço NLP para entender a intenção
// System.out.println("Mensagem do usuário: " + message.getContent());
// String intent = nlpService.getIntent(message.getContent());

// // Envie a mensagem do usuário para o tópico de chat
// messagingTemplate.convertAndSend("/topic/chat", message);

// // Determine a resposta com base na intenção
// String userResponse = message.getContent(); // Aqui você pode usar a mensagem
// do usuário para a resposta
// String response = nlpService.respondToIntent(intent, userResponse); // Passe
// a mensagem do usuário para o método

// // Crie uma mensagem de resposta
// ChatBot botResponse = new ChatBot();
// botResponse.setContent(response);
// botResponse.setSender("ChatBot");
// botResponse.setType(ChatBot.MessageType.CHAT);
// botResponse.setEndCoversation(false);

// // Envie a mensagem de resposta para o tópico de chat
// messagingTemplate.convertAndSend("/topic/chat", botResponse);
// return response;
// }
