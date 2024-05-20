package org.unibl.etf.ip.webshop.services;

import org.unibl.etf.ip.webshop.exceptions.NotFoundException;
import org.unibl.etf.ip.webshop.models.dto.AttributeDTO;
import org.unibl.etf.ip.webshop.models.dto.ProductAttributeDTO;

import java.util.List;

public interface AttributeService {
    List<AttributeDTO> getAllByCategoryId(Integer id);

    AttributeDTO findById(Integer id) throws NotFoundException;

    List<ProductAttributeDTO> getAllByProductId(Integer id);

    void delete(Integer attributeId, Integer productId) throws NotFoundException;
}
