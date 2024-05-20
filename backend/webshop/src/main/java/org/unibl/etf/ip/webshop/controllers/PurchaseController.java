package org.unibl.etf.ip.webshop.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.ip.webshop.exceptions.ConflictException;
import org.unibl.etf.ip.webshop.exceptions.NotFoundException;
import org.unibl.etf.ip.webshop.exceptions.UnauthorizedException;
import org.unibl.etf.ip.webshop.models.dto.PurchaseDTO;
import org.unibl.etf.ip.webshop.models.entities.PurchaseEntity;

import org.unibl.etf.ip.webshop.services.PurchaseService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/purchases")
public class PurchaseController{

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping("/{id}")
    ResponseEntity<PurchaseDTO> findOne(@PathVariable("id")Integer id) throws NotFoundException {
        return ResponseEntity.ok(purchaseService.findById(id));
    }


    @GetMapping("/{id}/product")
    ResponseEntity<PurchaseDTO> findOneByProduct(@PathVariable("id")Integer id) throws NotFoundException {
        return ResponseEntity.ok(purchaseService.getByProductId(id));
    }

    @GetMapping("/{id}/user")
    ResponseEntity<List<PurchaseDTO>> findAllByBuyer(@PathVariable("id")Integer id) throws NotFoundException {
        return ResponseEntity.ok(purchaseService.getAllByBuyerId(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<PurchaseDTO> insert(@Valid @RequestBody PurchaseDTO purchase) throws ConflictException, NotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(purchaseService.insert((purchase)));
    }
}
