package org.unibl.etf.ip.webshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.unibl.etf.ip.webshop.models.entities.ProductHasAttributeEntity;
import org.unibl.etf.ip.webshop.models.entities.ProductHasAttributeEntityPK;

import java.util.Arrays;
import java.util.List;

public interface ProductHasAttributeRepository extends JpaRepository<ProductHasAttributeEntity ,ProductHasAttributeEntityPK> {
    @Query("SELECT ap FROM ProductHasAttributeEntity ap WHERE ap.productId = ?1")
    List<ProductHasAttributeEntity> findByProductId(Integer id);

    @Query("SELECT ap FROM ProductHasAttributeEntity ap WHERE ap.productId = ?1 and ap.attributeId = ?2")
    List<ProductHasAttributeEntity> findByIds(Integer id1, Integer id2);

    @Modifying
    @Query(value = "DELETE FROM ProductHasAttributeEntity pha WHERE pha.productId = :id1 and pha.attributeId= :id2")
    void deleteByIdCustom(Integer id1, Integer id2);


    @Query("SELECT pha FROM ProductHasAttributeEntity pha WHERE pha.productId = :id1 and pha.attributeId= :id2")
    ProductHasAttributeEntity findByIdCustom(Integer id1, Integer id2);

}
