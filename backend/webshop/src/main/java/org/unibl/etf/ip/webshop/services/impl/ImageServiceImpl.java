package org.unibl.etf.ip.webshop.services.impl;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.unibl.etf.ip.webshop.exceptions.NotFoundException;
import org.unibl.etf.ip.webshop.exceptions.UnauthorizedException;
import org.unibl.etf.ip.webshop.models.dto.ImageDTO;
import org.unibl.etf.ip.webshop.models.dto.ProductDTO;
import org.unibl.etf.ip.webshop.models.entities.ImageEntity;
import org.unibl.etf.ip.webshop.models.entities.ProductEntity;
import org.unibl.etf.ip.webshop.repositories.ImageEntityRepository;
import org.unibl.etf.ip.webshop.repositories.ProductEntityRepository;
import org.unibl.etf.ip.webshop.services.AuthService;
import org.unibl.etf.ip.webshop.services.ImageService;
import org.unibl.etf.ip.webshop.util.ImageUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ImageServiceImpl implements ImageService {

    private final ImageEntityRepository imageEntityRepo;
    private final ProductEntityRepository productEntityRepo;

    private final AuthService authService;

    private final ModelMapper mapper;

    public ImageServiceImpl(ImageEntityRepository imageEntityRepo, ProductEntityRepository productEntityRepo, AuthService authService, ModelMapper mapper) {
        this.imageEntityRepo = imageEntityRepo;
        this.productEntityRepo = productEntityRepo;
        this.authService = authService;
        this.mapper = mapper;
    }

    @Override
    public List<ImageDTO> findAllByProductId(Integer id) {
        return imageEntityRepo.getAllByProductId(id).stream().map(i -> {
           ImageDTO im = mapper.map(i, ImageDTO.class);
           im.setData(ImageUtils.codeToString(i.getData()));

           return im;
        }).collect(Collectors.toList());
    }

    @Override
    public ImageDTO insert(ImageDTO im) throws NotFoundException {



        if(!productEntityRepo.existsById(im.getProductId())) throw new NotFoundException();

        ProductEntity product = productEntityRepo.findById(im.getProductId()).orElse(null);
        if (!authService.verifyUserId(product.getUser().getId())) {
            throw new NotFoundException();
        }

        ImageEntity i = mapper.map(im, ImageEntity.class);
        i.setData(ImageUtils.decodeBase64Image(im.getData()));
        ImageEntity ime =  imageEntityRepo.saveAndFlush(i);
        return mapper.map(ime, ImageDTO.class);
    }

    @Override
    public boolean delete(Integer id) throws NotFoundException {
        ImageEntity image = imageEntityRepo.findById(id).orElse(null);
        if (image == null) throw new NotFoundException();


        ProductEntity product = productEntityRepo.findById(image.getProduct().getId()).orElse(null);

        if (!authService.verifyUserId(product.getUser().getId())) throw new NotFoundException();
        imageEntityRepo.deleteById(image.getId());
        return true;
    }

    @Override
    public ImageDTO findById(Integer id) throws NotFoundException {
        return mapper.map(imageEntityRepo.findById(id).orElseThrow(NotFoundException::new), ImageDTO.class);
    }

    @Override
    public Integer deleteAllByProductId(Integer id) {
        return imageEntityRepo.deleteAllByProductId(id);
    }

    @Override
    public ImageDTO findOneByProductId(Integer id) throws NotFoundException {
        ImageDTO im = new ImageDTO();
        List<ImageEntity> ie = imageEntityRepo.getOneByProductId(id);

        if(ie.size() == 0) throw new NotFoundException();

        im.setData(ImageUtils.codeToString(ie.get(0).getData()));
        im.setId(ie.get(0).getId());
        im.setProductId(ie.get(0).getProduct().getId());
        return im;
    }

//    @Override
//    public List<ImageDTO> findAll() {
//        return imageEntityRepo.findAll().stream().map(c -> mapper.map(c, ImageDTO.class)).collect(Collectors.toList());
//    }
}
