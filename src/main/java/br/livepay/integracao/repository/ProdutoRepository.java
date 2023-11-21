package br.livepay.integracao.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import br.livepay.integracao.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    Produto findByName(String nomeDoProduto);
}
