package org.unibl.etf.ip.webshop.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.ip.webshop.exceptions.NotFoundException;
import org.unibl.etf.ip.webshop.models.dto.CommentDTO;
import org.unibl.etf.ip.webshop.models.dto.ImageDTO;
import org.unibl.etf.ip.webshop.services.CommentService;
import org.unibl.etf.ip.webshop.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {

    private final CommentService commentService;


    public CommentController(CommentService commentService) {
        this.commentService = commentService;

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<CommentDTO> insert(@Valid @RequestBody CommentDTO comment) throws NotFoundException {

        return ResponseEntity.ok(commentService.insert((comment)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<CommentDTO>> findAllByProductId(@Valid @PathVariable("id") Integer id) throws NotFoundException{
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(commentService.findAllByProductId(id));
    }
}
