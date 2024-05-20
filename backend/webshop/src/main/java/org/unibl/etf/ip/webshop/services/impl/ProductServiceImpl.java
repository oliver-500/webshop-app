package org.unibl.etf.ip.webshop.services.impl;

import org.aspectj.weaver.ast.Not;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.unibl.etf.ip.webshop.exceptions.ConflictException;
import org.unibl.etf.ip.webshop.exceptions.NotFoundException;
import org.unibl.etf.ip.webshop.exceptions.UnauthorizedException;
import org.unibl.etf.ip.webshop.models.dto.*;
import org.unibl.etf.ip.webshop.models.entities.*;
import org.unibl.etf.ip.webshop.repositories.*;
import org.unibl.etf.ip.webshop.services.AttributeService;
import org.unibl.etf.ip.webshop.services.AuthService;
import org.unibl.etf.ip.webshop.services.ProductService;
import org.unibl.etf.ip.webshop.services.UserService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductEntityRepository productRepo;

    private final CategoryEntityRepository categoryRepo;

    private final AttributeEntityRepository attributeRepo;

    private final UserService userService;

    private final AuthService authService;
    public static final String newCondition = "New";
    public static final String oldCondition = "Used";


    private final ProductHasAttributeRepository productHasAttributeRepo;

    private final ProductHasCategoryRepository productHasCategoryRepository;

    private final ModelMapper mapper;

    public ProductServiceImpl(ProductEntityRepository productRepo, CategoryEntityRepository categoryRepo, AttributeEntityRepository attributeRepo, UserService userService, AuthService authService, ProductHasAttributeRepository productHasAttributeRepo, ProductHasCategoryRepository productHasCategoryRepository, ModelMapper mapper) {
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
        this.attributeRepo = attributeRepo;
        this.userService = userService;
        this.authService = authService;
        this.productHasAttributeRepo = productHasAttributeRepo;
        this.productHasCategoryRepository = productHasCategoryRepository;
        this.mapper = mapper;

    }

    @Override
    public ProductDTO findById(Integer id) throws NotFoundException {
        ProductEntity p = productRepo.findById(id).orElseThrow(NotFoundException::new);

        ProductDTO pr = new ProductDTO();
        mapper.map(p, pr);

        pr.setSellerId(p.getUser().getId());


        return pr;
    }

    @Override
    public List<ProductDTO> lookUp(String username, String condition, Integer categoryId, BigDecimal lPrice, BigDecimal hPrice, String keyword) throws NotFoundException {

        List<ProductEntity> result = new ArrayList<>();

        if (username.length() > 0) {
            if (!authService.verifyUserId(userService.findByUsername(username).getId())) throw new NotFoundException();
        }

        if (keyword.length() != 0) {
            result.addAll(productRepo.findByKeyword(keyword));
        } else {
            result.addAll(productRepo.findAll());
        }

        if (username.length() != 0) {

            result = result.stream().filter(p -> {

                return p.getUser().getUsername().equals(username);


            }).collect(Collectors.toList());
        }
        if (condition.length() != 0) {
            if (newCondition.equals(condition))
                result = result.stream().filter(p -> p.getUsed() == false).collect(Collectors.toList());
            else if (oldCondition.equals(condition))
                result = result.stream().filter(p -> p.getUsed() == true).collect(Collectors.toList());
            else throw new NotFoundException();
        }
        if (lPrice != null && lPrice.compareTo(BigDecimal.ZERO) > 0) {
            result = result.stream().filter(p -> p.getPrice().compareTo(lPrice) > 0).collect(Collectors.toList());
        }
        if (hPrice != null && hPrice.compareTo(BigDecimal.ZERO) > 0) {
            result = result.stream().filter(p -> p.getPrice().compareTo(hPrice) < 0).collect(Collectors.toList());
        }
        if (categoryId != -1) {
            List<Integer> productIds = productHasCategoryRepository.findAllByCategoryId(categoryId).stream().map(p -> p.getProductId()).collect(Collectors.toList());
            result = result.stream().filter(p -> {
                return productIds.contains(p.getId());
            }).collect(Collectors.toList());
        }

        if (username.length() == 0)
            return result.stream().map(p -> {
                ProductDTO pro = new ProductDTO();
                pro.setSellerId(p.getUser().getId());
                mapper.map(p, pro);
                return pro;
            }).filter(p -> !p.getSold()).collect(Collectors.toList());
        else return result.stream().map(p -> {
            ProductDTO pro = new ProductDTO();
            pro.setSellerId(p.getUser().getId());
            mapper.map(p, pro);
            return pro;
        }).collect(Collectors.toList());

    }

    @Override
    public ProductDTO insert(ProductDTO product) throws NotFoundException {

        if (!this.authService.verifyUserId(product.getSellerId())) throw new NotFoundException();

        ProductEntity p = mapper.map(product, ProductEntity.class);
        p.setId(null);

        p.setUser(mapper.map(userService.findByUserId(product.getSellerId()), UserEntity.class));
        return mapper.map(productRepo.saveAndFlush(p), ProductDTO.class);
    }

    @Override
    public ProductCategoryDTO addCategory(ProductCategoryDTO productCategory) throws NotFoundException, ConflictException {

        ProductEntity pe = productRepo.findById(productCategory.getProductId()).orElse(null);
        if (pe == null) throw new NotFoundException();

        CategoryEntity ce = categoryRepo.findById(productCategory.getCategoryId()).orElse(null);
        if (ce == null) throw new NotFoundException();
        if (!authService.verifyUserId(pe.getUser().getId())) throw new NotFoundException();
        List<ProductHasCategoryEntity> lista = productHasCategoryRepository.findAllByProductId(productCategory.getProductId());


        if (lista.stream().filter(l -> l.getCategoryId().equals(productCategory.getCategoryId())).collect(Collectors.toList()).size() > 0)
            throw new ConflictException();
        ProductDTO p = findById(productCategory.getProductId());
        CategoryEntity c = categoryRepo.findById(productCategory.getCategoryId()).orElse(null);
        if (p == null || c == null) throw new NotFoundException();
        ProductHasCategoryEntity phce = new ProductHasCategoryEntity();
        phce.setCategoryId(productCategory.getCategoryId());
        phce.setProductId(productCategory.getProductId());


        return mapper.map((productHasCategoryRepository.saveAndFlush(phce)), ProductCategoryDTO.class);
    }

    @Override
    public ProductDTO setAttribute(ProductAttributeDTO productAttribute) throws NotFoundException {
        if (!productRepo.existsById(productAttribute.getProductId()) || !attributeRepo.existsById(productAttribute.getAttributeId()))
            throw new NotFoundException();

        ProductEntity pe = productRepo.findById(productAttribute.getProductId()).orElse(null);
        if(pe == null) throw new NotFoundException();
        if (!authService.verifyUserId(pe.getUser().getId())) throw new NotFoundException();

        ProductHasAttributeEntity pha = mapper.map(productAttribute, ProductHasAttributeEntity.class);
        pha.setProductId(productAttribute.getProductId());
        pha.setAttributeId(productAttribute.getAttributeId());
        pha.setValue(productAttribute.getValue());


        productHasAttributeRepo.saveAndFlush(pha);
        return findById(productAttribute.getProductId());

    }

    @Override
    public void delete(Integer id) throws NotFoundException {

        ProductEntity pe = productRepo.findById(id).orElse(null);
        if (pe == null)
            throw new NotFoundException();

        if (!authService.verifyUserId(pe.getUser().getId())) throw new NotFoundException();
        productRepo.deleteByIdCustom(id);


    }

    @Override
    public List<ProductDTO> findAllUnsold() {
        return productRepo.findAll().stream().map(p -> {
            ProductDTO pd = new ProductDTO();

            mapper.map(p, pd);
            pd.setSellerId(p.getUser().getId());
            return pd;

        }).filter(p -> !p.getSold()).collect(Collectors.toList());
    }



    @Override
    public List<ProductDTO> findBySellerId(Integer id) throws NotFoundException {

        if (!authService.verifyUserId(id)) throw new NotFoundException();
        return productRepo.findAllByUserId(id).stream().map(p -> {
            ProductDTO pro = new ProductDTO();
            pro.setSellerId(p.getUser().getId());
            mapper.map(p, pro);
            return pro;
        }).collect(Collectors.toList());

    }





    @Override
    public ProductDTO update(Integer id, ProductDTO p) throws NotFoundException {
        ProductEntity pe = productRepo.findById(id).orElseThrow(NotFoundException::new);
        if (pe == null) throw new NotFoundException();

        if (!authService.verifyUserId(pe.getUser().getId())) throw new NotFoundException();

        pe.setUsed(p.getUsed());
        pe.setSold(p.getSold());
        pe.setTitle(p.getTitle());
        pe.setContact(p.getContact());
        pe.setLocation(p.getContact());
        pe.setPrice(p.getPrice());
        pe.setDescription(p.getDescription());
        pe.setUser(mapper.map(userService.findByUserId(p.getSellerId()), UserEntity.class));

        return mapper.map(productRepo.saveAndFlush(pe), ProductDTO.class);


    }

    @Override
    public ProductDTO updateAttribute(ProductAttributeDTO productAttribute) throws NotFoundException {
        if (!productRepo.existsById(productAttribute.getProductId()) || !attributeRepo.existsById(productAttribute.getAttributeId()))
            throw new NotFoundException();

        ProductEntity pe = productRepo.findById(productAttribute.getProductId()).orElse(null);
        if(pe == null) throw new NotFoundException();
        if (!authService.verifyUserId(pe.getUser().getId())) throw new NotFoundException();

        ProductHasAttributeEntity pha = productHasAttributeRepo.findByIdCustom(productAttribute.getProductId(), productAttribute.getAttributeId());
        if(pha == null) throw new NotFoundException();

        pha.setValue(productAttribute.getValue());


        productHasAttributeRepo.saveAndFlush(pha);
        return findById(productAttribute.getProductId());
    }


}
