package br.livepay.integracao.service;

// import org.springframework.beans.factory.annotation.Autowired;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

import br.livepay.integracao.model.ChatBot;
import br.livepay.integracao.model.ChatBot.MessageType;
import br.livepay.integracao.repository.ChatBotRepository;

@Service
public class ChatBotService {

    private final ChatBotRepository chatBotRepository;
    private final NLPService nlpService;

    public ChatBotService(ChatBotRepository chatBotRepository, NLPService nlpService) {
        this.chatBotRepository = chatBotRepository;
        this.nlpService = nlpService;
    }

    public ChatBot sendMessage(String content, String sender) {
        ChatBot chatMessage = new ChatBot();
        chatMessage.setContent(content);
        chatMessage.setSender(sender);
        chatMessage.setType(MessageType.CHAT);
        chatMessage.setEndCoversation(false);
    
        // Use NLPService to get the intent of the message
        String intent = nlpService.getIntent(content);
    
        // Use NLPService to generate a response based on the intent
        String response = nlpService.respondToIntent(intent, content);
    
        // Set the response in the chat message
        chatMessage.setResponse(response);
    
        return chatBotRepository.save(chatMessage);
    }

    public ChatBot joinChat(String sender) {
        ChatBot chatMessage = new ChatBot();
        chatMessage.setSender(sender);
        chatMessage.setType(MessageType.JOIN);
        chatMessage.setEndCoversation(false);
        return chatBotRepository.save(chatMessage);
    }

    public ChatBot leaveChat(String sender) {
        ChatBot chatMessage = new ChatBot();
        chatMessage.setSender(sender);
        chatMessage.setType(MessageType.LEAVE);
        chatMessage.setEndCoversation(true);
        return chatBotRepository.save(chatMessage);
    }
}
