package org.unibl.etf.ip.webshop.controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.ip.webshop.exceptions.NotFoundException;
import org.unibl.etf.ip.webshop.models.dto.AttributeDTO;
import org.unibl.etf.ip.webshop.models.dto.CategoryDTO;
import org.unibl.etf.ip.webshop.services.AttributeService;
import org.unibl.etf.ip.webshop.services.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private final CategoryService service;
    private final AttributeService attributeService;

    public CategoryController(CategoryService service, AttributeService attributeService) {
        this.service = service;
        this.attributeService = attributeService;
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<CategoryDTO> findByProductId(@PathVariable("productId") Integer id) throws NotFoundException {
        return ResponseEntity.ok(service.findByProductId(id));
    }



    @GetMapping("/{id}")
    ResponseEntity<CategoryDTO> findOne(@PathVariable("id") Integer id) throws NotFoundException {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    ResponseEntity<List<CategoryDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}/attributes")
    ResponseEntity<List<AttributeDTO>> getAllAttributesForCategory(@PathVariable("id") Integer id){
        return ResponseEntity.ok(attributeService.getAllByCategoryId(id));
    }

    @DeleteMapping("/{id1}/{id2}")
    void deleteById(@PathVariable("id2") Integer categoryId, @PathVariable("id1") Integer productId) throws NotFoundException{
        service.delete(categoryId, productId);
    }

}
