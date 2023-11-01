package br.livepay.integracao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import br.livepay.integracao.model.Comment;
import br.livepay.integracao.service.CommentService;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping
    public ResponseEntity<Comment> save(@RequestBody Comment comment) {
        Comment savedComment = commentService.save(comment);
        return new ResponseEntity<>(savedComment, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Comment>> findByInterested(@RequestParam(required = false) Boolean interested) {
        List<Comment> comments;
        if (interested != null) {
            comments = commentService.findByInterested(interested);
        } else {
            comments = commentService.findAll();
        }
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

}
