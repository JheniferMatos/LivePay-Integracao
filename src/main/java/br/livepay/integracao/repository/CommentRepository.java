package br.livepay.integracao.repository;

import java.util.Optional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.livepay.integracao.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findById(Long id);

    List<Comment> findByInterested(Boolean interested);

    List<Comment> findByContentContaining(String content);

}