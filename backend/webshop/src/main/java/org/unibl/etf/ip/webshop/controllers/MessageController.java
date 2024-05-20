package org.unibl.etf.ip.webshop.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.ip.webshop.exceptions.NotFoundException;
import org.unibl.etf.ip.webshop.models.dto.MessageDTO;
import org.unibl.etf.ip.webshop.models.entities.*;

import org.unibl.etf.ip.webshop.services.MessageService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/messages")
public class MessageController{

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<MessageDTO> insert(@Valid @RequestBody MessageDTO message) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(messageService.insert(message));
    }
}

