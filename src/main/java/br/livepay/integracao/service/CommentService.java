package br.livepay.integracao.service;

import br.livepay.integracao.model.Comment;
import br.livepay.integracao.repository.CommentRepository;
import java.util.List;
// import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private CommentRepository commentRepository;
    private WordScores wordScores;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
        this.wordScores = new WordScores();
    }

    public Comment save(Comment comment) {
        if (isInterested(comment)) {
            comment.setInterested(true);
        } else {
            comment.setInterested(false);
        }
        return commentRepository.save(comment);
    }

    public List<Comment> findByInterested(Boolean interested) {
        return commentRepository.findByInterested(interested);
    }

    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    private boolean isInterested(Comment comment) {
        double score = 0.0;
        for (String word : comment.getContent().toLowerCase().split(" ")) {
            Double wordScore = wordScores.getWordScore(word);
            if (wordScore != null) {
                score += wordScore;
            }
        }
    
        return score >= 0.5;
    }
}
