package org.unibl.etf.ip.webshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.ip.webshop.models.entities.ImageEntity;
import org.unibl.etf.ip.webshop.models.entities.ProductEntity;
import org.unibl.etf.ip.webshop.models.entities.PurchaseEntity;

import java.util.List;

public interface PurchaseEntityRepository extends JpaRepository<PurchaseEntity, Integer> {
    PurchaseEntity getByProductId(Integer id);

    List<PurchaseEntity> getAllByUserId(Integer id);
}
