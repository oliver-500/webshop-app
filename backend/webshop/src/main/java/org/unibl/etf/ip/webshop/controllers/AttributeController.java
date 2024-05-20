package org.unibl.etf.ip.webshop.controllers;

import org.aspectj.weaver.ast.Not;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.ip.webshop.exceptions.NotFoundException;
import org.unibl.etf.ip.webshop.models.dto.AttributeDTO;
import org.unibl.etf.ip.webshop.models.dto.CategoryDTO;
import org.unibl.etf.ip.webshop.models.dto.ProductAttributeDTO;
import org.unibl.etf.ip.webshop.models.entities.AttributeEntity;
import org.unibl.etf.ip.webshop.repositories.AttributeEntityRepository;
import org.unibl.etf.ip.webshop.services.AttributeService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/attributes")
public class AttributeController {

    public final AttributeService service;

    public AttributeController(AttributeService service) {
        this.service = service;
    }



    @GetMapping
    List<AttributeEntity> findAll(){
        return null;
    }

    @GetMapping("/{id}")
    ResponseEntity<AttributeDTO> findOne(@PathVariable("id") Integer id) throws NotFoundException {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/product/{id}")
    ResponseEntity<List<ProductAttributeDTO>> findByProductId(@PathVariable("id") Integer id) throws NotFoundException{
        if(service.getAllByProductId(id).size() == 0) throw new NotFoundException();
        return ResponseEntity.ok(service.getAllByProductId(id));
    }

    @DeleteMapping("/{id1}/{id2}")
    void deleteById(@PathVariable("id2") Integer attributeId, @PathVariable("id1") Integer productId) throws NotFoundException{
        service.delete(attributeId, productId);
    }


}
