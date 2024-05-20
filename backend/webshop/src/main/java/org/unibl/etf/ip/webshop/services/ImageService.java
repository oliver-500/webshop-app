package org.unibl.etf.ip.webshop.services;

import org.unibl.etf.ip.webshop.exceptions.NotFoundException;
import org.unibl.etf.ip.webshop.models.dto.ImageDTO;

import java.util.List;

public interface ImageService {
    List<ImageDTO> findAllByProductId(Integer id);

    ImageDTO insert(ImageDTO im) throws NotFoundException;

    boolean delete(Integer id) throws NotFoundException;

    ImageDTO findById(Integer id) throws NotFoundException;

    Integer deleteAllByProductId(Integer id) throws NotFoundException;

    ImageDTO findOneByProductId(Integer id) throws NotFoundException;

}
