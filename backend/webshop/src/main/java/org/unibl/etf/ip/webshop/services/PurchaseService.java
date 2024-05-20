package org.unibl.etf.ip.webshop.services;

import org.aspectj.weaver.ast.Not;
import org.unibl.etf.ip.webshop.exceptions.ConflictException;
import org.unibl.etf.ip.webshop.exceptions.NotFoundException;
import org.unibl.etf.ip.webshop.exceptions.UnauthorizedException;
import org.unibl.etf.ip.webshop.models.dto.ProductDTO;
import org.unibl.etf.ip.webshop.models.dto.PurchaseDTO;

import java.util.List;

public interface PurchaseService {
    PurchaseDTO findById(Integer id) throws NotFoundException;

    PurchaseDTO insert(PurchaseDTO trader) throws ConflictException, NotFoundException;

    PurchaseDTO getByProductId(Integer id) throws NotFoundException;

    List<PurchaseDTO> getAllByBuyerId(Integer id) throws NotFoundException;
}
