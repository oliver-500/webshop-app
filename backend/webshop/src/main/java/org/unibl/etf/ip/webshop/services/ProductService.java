package org.unibl.etf.ip.webshop.services;

import org.unibl.etf.ip.webshop.exceptions.ConflictException;
import org.unibl.etf.ip.webshop.exceptions.NotFoundException;
import org.unibl.etf.ip.webshop.exceptions.UnauthorizedException;
import org.unibl.etf.ip.webshop.models.dto.CategoryDTO;
import org.unibl.etf.ip.webshop.models.dto.ProductAttributeDTO;
import org.unibl.etf.ip.webshop.models.dto.ProductCategoryDTO;
import org.unibl.etf.ip.webshop.models.dto.ProductDTO;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    ProductDTO findById(Integer id) throws NotFoundException;

    List<ProductDTO> lookUp(String username, String condition, Integer categoryId, BigDecimal lPrice, BigDecimal hPrice, String keyword) throws NotFoundException;


    ProductDTO insert(ProductDTO trader) throws NotFoundException;

    ProductCategoryDTO addCategory(ProductCategoryDTO productCategory) throws NotFoundException, ConflictException;

    ProductDTO setAttribute(ProductAttributeDTO productAttribute) throws NotFoundException;


    void delete(Integer id) throws NotFoundException;


    List<ProductDTO> findAllUnsold();


    List<ProductDTO> findBySellerId(Integer id) throws NotFoundException;


    ProductDTO update(Integer id, ProductDTO product) throws NotFoundException;

    ProductDTO updateAttribute(ProductAttributeDTO productAttribute) throws NotFoundException;
}
