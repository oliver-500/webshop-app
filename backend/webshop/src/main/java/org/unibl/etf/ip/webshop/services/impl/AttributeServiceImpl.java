package org.unibl.etf.ip.webshop.services.impl;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.unibl.etf.ip.webshop.exceptions.NotFoundException;
import org.unibl.etf.ip.webshop.models.dto.AttributeDTO;
import org.unibl.etf.ip.webshop.models.dto.ProductAttributeDTO;
import org.unibl.etf.ip.webshop.repositories.AttributeEntityRepository;
import org.unibl.etf.ip.webshop.repositories.ProductHasAttributeRepository;
import org.unibl.etf.ip.webshop.services.AttributeService;
import org.unibl.etf.ip.webshop.services.AuthService;
import org.unibl.etf.ip.webshop.services.ProductService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AttributeServiceImpl implements AttributeService {

    final private ModelMapper mapper;
    final private AttributeEntityRepository attributeRepo;
    final private ProductHasAttributeRepository productHasAttributeRepo;

    final private ProductService productService;

    final private AuthService authService;

    public AttributeServiceImpl(ModelMapper mapper, AttributeEntityRepository attributeRepo, ProductHasAttributeRepository productHasAttributeRepo, ProductService productService, AuthService authService) {
        this.mapper = mapper;
        this.attributeRepo = attributeRepo;
        this.productHasAttributeRepo = productHasAttributeRepo;
        this.productService = productService;
        this.authService = authService;
    }

    @Override
    public List<AttributeDTO> getAllByCategoryId(Integer id) {

        return attributeRepo.getAllByCategoryId(id).stream().map(a -> mapper.map(a, AttributeDTO.class)).collect(Collectors.toList());
    }

    @Override
    public AttributeDTO findById(Integer id) throws  NotFoundException{
        return mapper.map(attributeRepo.findById(id).orElseThrow(NotFoundException::new), AttributeDTO.class);
    }

    @Override
    public List<ProductAttributeDTO> getAllByProductId(Integer id) {
        return productHasAttributeRepo.findByProductId(id).stream().map(pa -> {
            return mapper.map(pa, ProductAttributeDTO.class);
        }).collect(Collectors.toList());
    }

    @Override
    public void delete(Integer attributeId, Integer productId) throws NotFoundException {

        if(!authService.verifyUserId(productService.findById(productId).getSellerId())) throw new NotFoundException();

        if(productHasAttributeRepo.findByIds(productId, attributeId ).size() == 0) throw new NotFoundException();

        productHasAttributeRepo.deleteByIdCustom(productId, attributeId );


    }
}
