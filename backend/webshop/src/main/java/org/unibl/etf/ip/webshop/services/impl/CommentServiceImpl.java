package org.unibl.etf.ip.webshop.services.impl;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.unibl.etf.ip.webshop.exceptions.NotFoundException;
import org.unibl.etf.ip.webshop.models.dto.CommentDTO;
import org.unibl.etf.ip.webshop.models.dto.ProductDTO;
import org.unibl.etf.ip.webshop.models.entities.CommentEntity;
import org.unibl.etf.ip.webshop.repositories.CommentEntityRepository;
import org.unibl.etf.ip.webshop.services.CommentService;
import org.unibl.etf.ip.webshop.services.ProductService;
import org.unibl.etf.ip.webshop.services.SecurityService;
import org.unibl.etf.ip.webshop.services.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentEntityRepository commentEntityRepo;

    private final UserService userService;

    private final ProductService productService;

    private final SecurityService securityService;

    private final ModelMapper mapper;
    public CommentServiceImpl(CommentEntityRepository commentEntityRepo, UserService userService, ProductService productService, SecurityService securityService, ModelMapper mapper) {
        this.commentEntityRepo = commentEntityRepo;
        this.userService = userService;
        this.productService = productService;
        this.securityService = securityService;
        this.mapper = mapper;
    }

    @Override
    public CommentDTO insert(CommentDTO comment) throws NotFoundException {

        if(userService.findByUsername(comment.getUsername()) == null) throw new NotFoundException();

        //da li user koji postavlja komentar postavlja za sebe
        if(!securityService.checkUsername(userService.findByUsername(comment.getUsername()).getId())){
            throw new NotFoundException();
        }

        //da li postoji proizvod za koji se komentar postavlja
        ProductDTO product = productService.findById(comment.getProductId());
        if(product == null) throw new NotFoundException();
        else if(product.getSold() == true) throw new NotFoundException();


        return mapper.map(commentEntityRepo.saveAndFlush(mapper.map(comment, CommentEntity.class)), CommentDTO.class);
    }

    @Override
    public List<CommentDTO> findAllByProductId(Integer id) {

        return commentEntityRepo.getAllByProductId(id).stream().map(c -> {
            CommentDTO co = mapper.map(c, CommentDTO.class);


            return co;
        }).collect(Collectors.toList());
    }
}
