package org.unibl.etf.ip.webshop.services;


import org.unibl.etf.ip.webshop.exceptions.IncorrectPINCodeException;
import org.unibl.etf.ip.webshop.exceptions.NotFoundException;
import org.unibl.etf.ip.webshop.models.dto.CategoryDTO;
import org.unibl.etf.ip.webshop.models.requests.UpdateUserRequest;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> findAll();

    CategoryDTO findById(Integer id) throws NotFoundException;

    CategoryDTO update(Integer id, UpdateUserRequest request) throws NotFoundException;

    CategoryDTO insert(CategoryDTO trader) throws NotFoundException;

    void delete(Integer id1, Integer id2) throws NotFoundException;

    CategoryDTO findByProductId(Integer id) throws NotFoundException;


}
