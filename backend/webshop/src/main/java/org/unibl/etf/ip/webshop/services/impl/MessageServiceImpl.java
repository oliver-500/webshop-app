package org.unibl.etf.ip.webshop.services.impl;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.unibl.etf.ip.webshop.exceptions.NotFoundException;
import org.unibl.etf.ip.webshop.models.dto.MessageDTO;
import org.unibl.etf.ip.webshop.models.entities.MessageEntity;
import org.unibl.etf.ip.webshop.repositories.MessageEntityRepository;
import org.unibl.etf.ip.webshop.repositories.UserEntityRepository;
import org.unibl.etf.ip.webshop.services.MessageService;

@Service
@Transactional
public class MessageServiceImpl implements MessageService {

    private final MessageEntityRepository messageRepo;

    private final UserEntityRepository userRepo;


    private final ModelMapper mapper;

    public MessageServiceImpl(MessageEntityRepository messageRepo, UserEntityRepository userRepo, ModelMapper mapper) {
        this.messageRepo = messageRepo;
        this.userRepo = userRepo;
        this.mapper = mapper;
    }

    @Override
    public MessageDTO insert(MessageDTO message) throws NotFoundException {
        if(!userRepo.existsById(message.getUserId())) throw new NotFoundException();
        MessageEntity m = mapper.map(message, MessageEntity.class);
        m.setMread(false);
        return  mapper.map(messageRepo.saveAndFlush(m), MessageDTO.class);

    }
}
