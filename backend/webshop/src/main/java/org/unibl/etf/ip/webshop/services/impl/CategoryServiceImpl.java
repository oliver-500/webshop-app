package org.unibl.etf.ip.webshop.services.impl;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.unibl.etf.ip.webshop.exceptions.NotFoundException;
import org.unibl.etf.ip.webshop.models.dto.CategoryDTO;
import org.unibl.etf.ip.webshop.models.entities.CategoryEntity;
import org.unibl.etf.ip.webshop.models.entities.ProductHasCategoryEntity;
import org.unibl.etf.ip.webshop.models.requests.UpdateUserRequest;
import org.unibl.etf.ip.webshop.repositories.CategoryEntityRepository;
import org.unibl.etf.ip.webshop.repositories.ProductHasCategoryRepository;
import org.unibl.etf.ip.webshop.services.AuthService;
import org.unibl.etf.ip.webshop.services.CategoryService;
import org.unibl.etf.ip.webshop.services.ProductService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final ModelMapper mapper;

    private final CategoryEntityRepository categoryRepo;

    private final AuthService authService;

    private final ProductService productService;

    private final ProductHasCategoryRepository productHasCategoryRepository;

    public CategoryServiceImpl(ModelMapper mapper, CategoryEntityRepository categoryRepo, AuthService authService, ProductService productService, ProductHasCategoryRepository productHasCategoryRepository) {
        this.mapper = mapper;
        this.categoryRepo = categoryRepo;
        this.authService = authService;
        this.productService = productService;
        this.productHasCategoryRepository = productHasCategoryRepository;
    }


    @Override
    public List<CategoryDTO> findAll() {
        return categoryRepo.findAll().stream().map(c -> mapper.map(c, CategoryDTO.class)).collect(Collectors.toList());
    }

    @Override
    public CategoryDTO findById(Integer id) throws NotFoundException {
        return mapper.map(categoryRepo.findById(id).orElseThrow(NotFoundException::new), CategoryDTO.class);
    }

    @Override
    public CategoryDTO update(Integer id, UpdateUserRequest request) throws NotFoundException{
        return null;
    }

    @Override
    public CategoryDTO insert(CategoryDTO trader) throws NotFoundException {
        return null;
    }

    @Override
    public void delete(Integer categoryId, Integer productId) throws NotFoundException {
        if(!authService.verifyUserId(productService.findById(productId).getSellerId())) throw new NotFoundException();
        if(productHasCategoryRepository.findByIds(productId, categoryId).size() == 0) throw new NotFoundException();

        productHasCategoryRepository.deleteByIdCustom(productId, categoryId);
    }

    @Override
    public CategoryDTO findByProductId(Integer id) throws NotFoundException {

        List<ProductHasCategoryEntity> phce = productHasCategoryRepository.findAllByProductId(id);
        if(phce.size() == 0) throw new NotFoundException();
        CategoryEntity cat = categoryRepo.findById(phce.get(0).getCategoryId()).orElse(null);
        if(cat != null) {
            CategoryDTO catDet = new CategoryDTO();
            catDet.setId(cat.getId());
            catDet.setName(cat.getName());
            return  catDet;
        }
        else throw new NotFoundException();

    }


}
