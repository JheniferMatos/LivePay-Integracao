package br.livepay.integracao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import br.livepay.integracao.controller.ComentariosController;
import br.livepay.integracao.model.Comentario;
import org.junit.jupiter.api.DisplayName;


@DisplayName("Testes para o ComentariosController")
@SpringBootTest
public class ComentariosControllerTest {

    @Autowired
    private ComentariosController comentariosController;

    @Test
    @DisplayName("Teste para monitorarComentario")
    public void testMonitorarComentario() {
        // Comentários simulados
        Comentario comentario1 = new Comentario("Quero comprar 3 camisetas.");
        Comentario comentario2 = new Comentario("Gostaria de adquirir 2 bonés.");
        Comentario comentario3 = new Comentario("Estou interessado em comprar 5 sapatos.");
        Comentario comentario4 = new Comentario("Ótima live!");
        Comentario comentario5 = new Comentario("Estou assistindo da Argentina.");
        Comentario comentario6 = new Comentario("Vocês são incríveis!");
        Comentario comentario7 = new Comentario("Eu quero 1 celular.");
        Comentario comentario8 = new Comentario("Quero comprar 2 ingressos para o show.");

        // Crie um array ou lista com todos os comentários simulados
        Comentario[] comentarios = { comentario1, comentario2, comentario3, comentario4, comentario5, comentario6,
                comentario7, comentario8 };

        for (Comentario comentario : comentarios) {
            comentariosController.monitorarComentario(comentario);
        }
    }
}
