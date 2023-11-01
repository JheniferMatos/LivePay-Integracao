package br.livepay.integracao.model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
// import br.livepay.integracao.repository.test.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;

@Entity
@Data
@Table
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ChatBot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private String sender;
    private MessageType type;
    private boolean EndCoversation;
    private String response;
    
    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }

}
