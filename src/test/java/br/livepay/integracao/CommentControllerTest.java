package br.livepay.integracao;

import br.livepay.integracao.model.Comment;
import br.livepay.integracao.service.CommentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@DisplayName("Testes para o LiveCommerceController")
public class CommentControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @MockBean
        private CommentService commentService;

        private Comment comment1;
        private Comment comment2;
        private Comment comment3;

        @BeforeEach
        public void setUp() {
                // Crie alguns comentários para teste
                comment1 = new Comment(32L, "Alice", "Eu gostei muito do seu produto", true);
                comment2 = new Comment(33L, "Bob", "Eu não achei interessante", false);
                comment3 = new Comment(34L, "Carol", "Eu quero saber mais sobre o seu produto", true);
        }

    @Test
    public void testSave() throws Exception {
        // Simule o serviço para retornar o comentário salvo
        when(commentService.save(any(Comment.class))).thenReturn(comment1);

        // Execute uma solicitação POST com o comentário em formato JSON
        mockMvc.perform(post("/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(comment1)))
                .andExpect(status().isCreated()) // Espera um código de status 201
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)) // Espera uma resposta em JSON
                .andExpect(jsonPath("$.id").value(32L)) // Espera que o ID seja 32
                .andExpect(jsonPath("$.author").value("Alice")) // Espera que o autor seja Alice
                .andExpect(jsonPath("$.content").value("Eu gostei muito do seu produto")) // Espera que o conteúdo seja o mesmo do comentário
                .andExpect(jsonPath("$.interested").value(true)); // Espera que a bandeira "interested" seja verdadeira
    }

        @Test
        public void testFindByInterested() throws Exception {
                // Simule o serviço para retornar uma lista de comentários interessados
                List<Comment> interestedComments = Arrays.asList(comment1, comment3);
                when(commentService.findByInterested(true)).thenReturn(interestedComments);

                // Execute uma solicitação GET com o parâmetro "interested" como verdadeiro
                mockMvc.perform(get("/comments?interested=true"))
                                .andExpect(status().isOk()) // Espera um código de status 200
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON)) // Espera uma resposta em
                                                                                              // JSON
                                .andExpect(jsonPath("$", hasSize(2))) // Espera uma lista de tamanho 2
                                .andExpect(jsonPath("$[0].id").value(32L)) // Espera que o primeiro comentário tenha o
                                                                           // ID 32
                                .andExpect(jsonPath("$[0].author").value("Alice")) // Espera que o primeiro comentário
                                                                                   // tenha o autor Alice
                                .andExpect(jsonPath("$[0].content").value("Eu gostei muito do seu produto")) // Espera
                                                                                                             // que o
                                                                                                             // primeiro
                                                                                                             // comentário
                                                                                                             // tenha o
                                                                                                             // mesmo
                                                                                                             // conteúdo
                                                                                                             // do
                                                                                                             // comment1
                                .andExpect(jsonPath("$[0].interested").value(true)) // Espera que o primeiro comentário
                                                                                    // tenha a bandeira "interested"
                                                                                    // como verdadeira
                                .andExpect(jsonPath("$[1].id").value(34L)) // Espera que o segundo comentário tenha o ID
                                                                           // 34
                                .andExpect(jsonPath("$[1].author").value("Carol")) // Espera que o segundo comentário
                                                                                   // tenha o autor Carol
                                .andExpect(jsonPath("$[1].content").value("Eu quero saber mais sobre o seu produto")) // Espera
                                                                                                                      // que
                                                                                                                      // o
                                                                                                                      // segundo
                                                                                                                      // comentário
                                                                                                                      // tenha
                                                                                                                      // o
                                                                                                                      // mesmo
                                                                                                                      // conteúdo
                                                                                                                      // do
                                                                                                                      // comment3
                                .andExpect(jsonPath("$[1].interested").value(true)); // Espera que o segundo comentário
                                                                                     // tenha a bandeira "interested"
                                                                                     // como verdadeira

                // Simule o serviço para retornar uma lista de comentários não interessados
                List<Comment> notInterestedComments = Arrays.asList(comment2);
                when(commentService.findByInterested(false)).thenReturn(notInterestedComments);

                // Execute uma solicitação GET com o parâmetro "interested" como falso
                mockMvc.perform(get("/comments?interested=false"))
                                .andExpect(status().isOk()) // Espera um código de status 200
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON)) // Espera uma resposta em
                                                                                              // JSON
                                .andExpect(jsonPath("$", hasSize(1))) // Espera uma lista de tamanho 1
                                .andExpect(jsonPath("$[0].id").value(33L)) // Espera que o comentário tenha o ID 33
                                .andExpect(jsonPath("$[0].author").value("Bob")) // Espera que o comentário tenha o
                                                                                 // autor Bob
                                .andExpect(jsonPath("$[0].content").value("Eu não achei interessante")) // Espera que o
                                                                                                        // comentário
                                                                                                        // tenha o mesmo
                                                                                                        // conteúdo do
                                                                                                        // comment2
                                .andExpect(jsonPath("$[0].interested").value(false)); // Espera que o comentário tenha a
                                                                                      // bandeira "interested" como
                                                                                      // falsa
        }

        @Test
        public void testFindAll() throws Exception {
                // Simule o serviço para retornar uma lista de todos os comentários
                List<Comment> allComments = Arrays.asList(comment1, comment2, comment3);
                when(commentService.findAll()).thenReturn(allComments);

                // Execute uma solicitação GET sem nenhum parâmetro
                mockMvc.perform(get("/comments"))
                                .andExpect(status().isOk()) // Espera um código de status 200
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON)) // Espera uma resposta em
                                                                                              // JSON
                                .andExpect(jsonPath("$", hasSize(3))) // Espera uma lista de tamanho 3
                                .andExpect(jsonPath("$[0].id").value(32L)) // Espera que o primeiro comentário tenha o
                                                                           // ID 32
                                .andExpect(jsonPath("$[0].author").value("Alice")) // Espera que o primeiro comentário
                                                                                   // tenha o autor Alice
                                .andExpect(jsonPath("$[0].content").value("Eu gostei muito do seu produto")) // Espera
                                                                                                             // que o
                                                                                                             // primeiro
                                                                                                             // comentário
                                                                                                             // tenha o
                                                                                                             // mesmo
                                                                                                             // conteúdo
                                                                                                             // do
                                                                                                             // comment1
                                .andExpect(jsonPath("$[0].interested").value(true)) // Espera que o primeiro comentário
                                                                                    // tenha a bandeira "interested"
                                                                                    // como verdadeira
                                .andExpect(jsonPath("$[1].id").value(33L)) // Espera que o segundo comentário tenha o ID
                                                                           // 33
                                .andExpect(jsonPath("$[1].author").value("Bob")) // Espera que o segundo comentário
                                                                                 // tenha o autor Bob
                                .andExpect(jsonPath("$[1].content").value("Eu não achei interessante")) // Espera que o
                                                                                                        // segundo
                                                                                                        // comentário
                                                                                                        // tenha o mesmo
                                                                                                        // conteúdo do
                                                                                                        // comment2
                                .andExpect(jsonPath("$[1].interested").value(false)) // Espera que o segundo comentário
                                                                                     // tenha a bandeira "interested"
                                                                                     // como falsa
                                .andExpect(jsonPath("$[2].id").value(34L)) // Espera que o terceiro comentário tenha o
                                                                           // ID 34
                                .andExpect(jsonPath("$[2].author").value("Carol")) // Espera que o terceiro comentário
                                                                                   // tenha o autor Carol
                                .andExpect(jsonPath("$[2].content").value("Eu quero saber mais sobre o seu produto")) // Espera
                                                                                                                      // que
                                                                                                                      // o
                                                                                                                      // terceiro
                                                                                                                      // comentário
                                                                                                                      // tenha
                                                                                                                      // o
                                                                                                                      // mesmo
                                                                                                                      // conteúdo
                                                                                                                      // do
                                                                                                                      // comment3
                                .andExpect(jsonPath("$[2].interested").value(true)); // Espera que o terceiro comentário
                                                                                     // tenha a bandeira "interested"
                                                                                     // como verdadeira
        }

        @Test
        public void testIsInterested() throws Exception {
                // Crie alguns comentários com diferentes pontuações para teste
                Comment positiveComment = new Comment(null, "Daniel", "Eu adorei o seu produto, é incrível", null);
                Comment negativeComment = new Comment(null, "Eduardo", "Eu odiei o seu produto, é péssimo", null);
                Comment neutralComment = new Comment(null, "Fernanda", "Eu achei o seu produto normal", null);

                // Simule o serviço para retornar os comentários com a bandeira "interested"
                // definida pelo método isInterested
                when(commentService.save(positiveComment)).thenCallRealMethod();
                when(commentService.save(negativeComment)).thenCallRealMethod();
                when(commentService.save(neutralComment)).thenCallRealMethod();

                // Execute uma solicitação POST com cada comentário e verifique se a bandeira
                // "interested" está correta

                mockMvc.perform(post("/comments")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(positiveComment)))
                                .andExpect(status().isCreated()) // Espera um código de status 201
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON)) // Espera uma resposta em
                                                                                              // JSON
                                .andExpect(jsonPath("$.interested").value(true)); // Espera que a bandeira "interested"
                                                                                  // seja verdadeira para o comentário
                                                                                  // positivo

                mockMvc.perform(post("/comments")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(negativeComment)))
                                .andExpect(status().isCreated()) // Espera um código de status 201
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON)) // Espera uma resposta em
                                                                                              // JSON
                                .andExpect(jsonPath("$.interested").value(false)); // Espera que a bandeira "interested"
                                                                                   // seja falsa para o comentário
                                                                                   // negativo

                mockMvc.perform(post("/comments")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(neutralComment)))
                                .andExpect(status().isCreated()) // Espera um código de status 201
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON)) // Espera uma resposta em
                                                                                              // JSON
                                .andExpect(jsonPath("$.interested").value(false)); // Espera que a bandeira "interested"
                                                                                   // seja falsa para o comentário
                                                                                   // neutro
        }
}
