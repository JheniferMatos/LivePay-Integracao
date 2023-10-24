package br.livepay.integracao.controller;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import br.livepay.integracao.model.Comentario;


@Controller
public class ComentariosController {


    @MessageMapping("/monitorarComentarios") 
    @SendTo("/topic/comentarios") 
    public Comentario monitorarComentario(Comentario comentario) {
        String textoComentario = comentario.getTexto();

        String[] palavras = textoComentario.toLowerCase().split(" ");

        int quantidade = -1; 
        String produto = null;

        // Procure o número e o produto nas palavras
        for (int i = 0; i < palavras.length; i++) {
            if (palavras[i].equals("quero")) {
                if (i + 1 < palavras.length) {
                    try {
                        quantidade = Integer.parseInt(palavras[i + 1]);
                    } catch (NumberFormatException e) {

                    }
                }
            } else if (i > 0 && palavras[i - 1].equals("quero")) {
                // Se a palavra anterior foi "quero," a palavra atual é o produto
                produto = palavras[i];
            }
        }

        if (quantidade != -1 && produto != null) {
            // Criar um pedido com base nas informações
            // Pedido pedido = new Pedido();
            // pedido.setQuantidade(quantidade);
            // pedido.setProduto(produto);

            // Você pode persistir o pedido em um banco de dados aqui

            // Notificar o comprador sobre o pedido em tempo real
            // notificacaoService.notificarComprador(pedido);

        }
        return comentario;
    }
}
