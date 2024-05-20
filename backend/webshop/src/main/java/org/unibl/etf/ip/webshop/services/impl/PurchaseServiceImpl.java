package org.unibl.etf.ip.webshop.services.impl;

import jakarta.transaction.Transactional;
import org.aspectj.weaver.ast.Not;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.unibl.etf.ip.webshop.exceptions.ConflictException;
import org.unibl.etf.ip.webshop.exceptions.NotFoundException;
import org.unibl.etf.ip.webshop.exceptions.UnauthorizedException;
import org.unibl.etf.ip.webshop.models.dto.PurchaseDTO;
import org.unibl.etf.ip.webshop.models.entities.ProductEntity;
import org.unibl.etf.ip.webshop.models.entities.PurchaseEntity;
import org.unibl.etf.ip.webshop.models.entities.UserEntity;
import org.unibl.etf.ip.webshop.models.enums.PayingMethod;
import org.unibl.etf.ip.webshop.repositories.ProductEntityRepository;
import org.unibl.etf.ip.webshop.repositories.PurchaseEntityRepository;
import org.unibl.etf.ip.webshop.repositories.UserEntityRepository;
import org.unibl.etf.ip.webshop.services.AuthService;
import org.unibl.etf.ip.webshop.services.PurchaseService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PurchaseServiceImpl implements PurchaseService {

    private final ModelMapper mapper;

    private final PurchaseEntityRepository purchaseRepo;
    private final ProductEntityRepository productRepo;
    private final UserEntityRepository userRepo;

    private final AuthService authService;

    public PurchaseServiceImpl(ModelMapper mapper, PurchaseEntityRepository purchaseRepo, ProductEntityRepository productRepo, UserEntityRepository userRepo, AuthService authService) {
        this.mapper = mapper;
        this.purchaseRepo = purchaseRepo;
        this.productRepo = productRepo;
        this.userRepo = userRepo;
        this.authService = authService;
    }

    @Override
    public PurchaseDTO findById(Integer id) throws NotFoundException {
        PurchaseEntity p = purchaseRepo.findById(id).orElseThrow(NotFoundException::new);

        return mapper.map(p, PurchaseDTO.class);


    }

    @Override
    public PurchaseDTO insert(PurchaseDTO purchase) throws ConflictException, NotFoundException {
        if(purchase.getPayingMethod() == null || (!purchase.getPayingMethod().equals(PayingMethod.card)
        && !purchase.getPayingMethod().equals(PayingMethod.delivery))) throw new NotFoundException();



        if (purchaseRepo.getByProductId(purchase.getProductId()) != null) throw new ConflictException();
        ProductEntity pe = productRepo.findById(purchase.getProductId()).orElse(null);
        if (pe == null) {
            throw new ConflictException();
        }


        UserEntity buyer = userRepo.findByUsername(authService.extractUserUsername());
        if (pe.getUser().getId().equals(buyer.getId())) throw new ConflictException();

        PurchaseEntity p = mapper.map(purchase, PurchaseEntity.class);
        p.setUser(buyer);


        pe.setSold(true);
        PurchaseEntity boughtPurchase = purchaseRepo.saveAndFlush(p);

        return mapper.map(boughtPurchase, PurchaseDTO.class);


    }

    @Override
    public PurchaseDTO getByProductId(Integer id) throws NotFoundException {
        return mapper.map(purchaseRepo.getByProductId(id), PurchaseDTO.class);
    }

    @Override
    public List<PurchaseDTO> getAllByBuyerId(Integer id) throws NotFoundException {


        if (!authService.verifyUserId(id)) throw new NotFoundException();

        return purchaseRepo.getAllByUserId(id).stream().map(p -> {

            PurchaseDTO pu = mapper.map(p, PurchaseDTO.class);
            pu.setTraderId(p.getUser().getId());
            return pu;
        }).collect(Collectors.toList());


    }
}
