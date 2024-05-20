package org.unibl.etf.ip.webshop.services;

import org.unibl.etf.ip.webshop.exceptions.NotFoundException;
import org.unibl.etf.ip.webshop.models.dto.CommentDTO;
import org.unibl.etf.ip.webshop.models.dto.MessageDTO;

import java.util.List;

public interface CommentService {
    public CommentDTO insert(CommentDTO comment) throws NotFoundException;

    public List<CommentDTO> findAllByProductId(Integer id);
}
