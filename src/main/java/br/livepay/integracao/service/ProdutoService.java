package br.livepay.integracao.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.livepay.integracao.model.Produto;
import br.livepay.integracao.repository.ProdutoRepository;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<Produto> obterTodosProdutos() {
        return produtoRepository.findAll();
    }

}