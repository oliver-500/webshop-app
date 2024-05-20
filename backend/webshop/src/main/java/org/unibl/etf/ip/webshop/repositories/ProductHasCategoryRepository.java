package org.unibl.etf.ip.webshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.unibl.etf.ip.webshop.models.entities.*;

import java.util.List;

public interface ProductHasCategoryRepository extends JpaRepository<ProductHasCategoryEntity, ProductHasCategoryEntityPK> {
    List<ProductHasCategoryEntity> findAllByCategoryId(Integer id);
    List<ProductHasCategoryEntity> findAllByProductId(Integer id);

    @Query("SELECT phc FROM ProductHasCategoryEntity phc WHERE phc.productId = ?1 and phc.categoryId = ?2")
    List<ProductHasCategoryEntity> findByIds(Integer id1, Integer id2);

    @Modifying
    @Query(value = "DELETE FROM ProductHasCategoryEntity phc WHERE phc.productId = :id1 and phc.categoryId= :id2")
    void deleteByIdCustom(Integer id1, Integer id2);
}
