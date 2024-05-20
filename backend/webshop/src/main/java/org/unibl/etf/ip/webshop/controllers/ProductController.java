package org.unibl.etf.ip.webshop.controllers;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.aspectj.weaver.ast.Not;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.ip.webshop.exceptions.ConflictException;
import org.unibl.etf.ip.webshop.exceptions.IncorrectPasswordException;
import org.unibl.etf.ip.webshop.exceptions.NotFoundException;
import org.unibl.etf.ip.webshop.exceptions.UnauthorizedException;
import org.unibl.etf.ip.webshop.models.dto.*;
import org.unibl.etf.ip.webshop.models.requests.UpdateUserRequest;
import org.unibl.etf.ip.webshop.services.AuthService;
import org.unibl.etf.ip.webshop.services.ImageService;
import org.unibl.etf.ip.webshop.services.ProductService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@Transactional
@RequestMapping("/api/v1/products")
public class ProductController {


    private final ProductService productService;

    private final AuthService authService;

    private final ImageService imageService;

    public ProductController(ProductService productService, AuthService authService, ImageService imageService) {
        this.productService = productService;
        this.authService = authService;
        this.imageService = imageService;
    }


    @GetMapping("")
    public ResponseEntity<List<ProductDTO>> findAll()  {
        return ResponseEntity.ok(productService.findAllUnsold());
    }



    @GetMapping("/search")
    public ResponseEntity<List<ProductDTO>> search(@RequestParam(value = "user") String user,
                                                    @RequestParam(value = "condition") String condition,
                                                   @RequestParam(value = "category") String category,
                                                   @RequestParam(value = "lowprice") String lowPrice,
                                                   @RequestParam(value = "highPrice") String highPrice,
                                                   @RequestParam(value = "keyword") String keyword) throws NotFoundException{

        try{
            BigDecimal lPrice = null;
            BigDecimal hPrice = null;
                    if(lowPrice.length() > 0) lPrice = new BigDecimal(lowPrice);
                    if(highPrice.length() > 0) hPrice = new BigDecimal(highPrice);
            Integer categoryId = -1;
            if(category.length() > 0) categoryId = Integer.parseInt(category);
            return ResponseEntity.ok(productService.lookUp(user, condition, categoryId, lPrice, hPrice, keyword));
        }
        catch(NumberFormatException nfe){
            throw new NotFoundException();
        }


    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> findOne(@PathVariable("id") Integer id) throws NotFoundException {
        return ResponseEntity.ok(productService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ProductDTO> insert(@RequestBody ProductDTO product) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.insert(product));
    }

    @PostMapping("/addCategory")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ProductCategoryDTO> addCategory(@Valid @RequestBody ProductCategoryDTO productCategory) throws NotFoundException, ConflictException {
        return ResponseEntity.ok(productService.addCategory(productCategory));
    }

    @PostMapping("/setAttribute")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ProductDTO> setAttribute(@Valid @RequestBody ProductAttributeDTO productAttribute) throws NotFoundException{
        return ResponseEntity.ok(productService.setAttribute(productAttribute));
    }

    @PutMapping("/updateAttribute")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ProductDTO> updateAttribute(@Valid @RequestBody ProductAttributeDTO productAttribute) throws NotFoundException{
        return ResponseEntity.ok(productService.updateAttribute(productAttribute));
    }

    @GetMapping("/{id}/images")
    public ResponseEntity<List<ImageDTO>> getImages(@PathVariable("id") Integer id){
        return ResponseEntity.ok(imageService.findAllByProductId(id));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id) throws NotFoundException {
        productService.delete(id);

    }


    @GetMapping("/user/{id}")
    public ResponseEntity<List<ProductDTO>> findByUserId(@PathVariable("id") Integer id) throws NotFoundException {
        return ResponseEntity.ok(productService.findBySellerId(id));
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<ProductDTO> update(@PathVariable("id") Integer id, @Valid @RequestBody ProductDTO product) throws NotFoundException {

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(productService.update(id, product));

    }
    
}
