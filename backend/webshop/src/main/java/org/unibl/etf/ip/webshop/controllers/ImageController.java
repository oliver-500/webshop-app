package org.unibl.etf.ip.webshop.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.ip.webshop.exceptions.NotFoundException;
import org.unibl.etf.ip.webshop.exceptions.UnauthorizedException;
import org.unibl.etf.ip.webshop.models.dto.ImageDTO;
import org.unibl.etf.ip.webshop.models.dto.ProductDTO;
import org.unibl.etf.ip.webshop.services.AuthService;
import org.unibl.etf.ip.webshop.services.ImageService;
import org.unibl.etf.ip.webshop.services.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/images")
public class ImageController {

    private final ImageService imageService;

    private final AuthService authService;
    private final ProductService productService;


    public ImageController(ImageService imageService, AuthService authService, ProductService productService) {
        this.imageService = imageService;
        this.authService = authService;
        this.productService = productService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<ImageDTO> insert(@Valid @RequestBody ImageDTO image) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(imageService.insert(image));
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<ImageDTO>> findAllByProductId(@PathVariable("id") Integer id) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(imageService.findAllByProductId(id));
    }


    @GetMapping("/{id}/one")
    public ResponseEntity<ImageDTO> findOneByProductId(@PathVariable("id") Integer id) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(imageService.findOneByProductId(id));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id) throws NotFoundException, UnauthorizedException {

        imageService.delete(id);

    }


//    @GetMapping
//    ResponseEntity<List<ImageDTO>> findAll() {
//        return ResponseEntity.ok(imageService.findAll());
//    }


    //izbrisati sve slike vezane za taj proizvod, jer ne moze cascade
}
