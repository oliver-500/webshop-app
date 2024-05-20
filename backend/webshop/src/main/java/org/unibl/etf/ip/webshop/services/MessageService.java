package org.unibl.etf.ip.webshop.services;

import org.unibl.etf.ip.webshop.exceptions.NotFoundException;
import org.unibl.etf.ip.webshop.models.dto.MessageDTO;

public interface MessageService {
    public MessageDTO insert(MessageDTO message) throws NotFoundException;
}
