package org.unibl.etf.ip.webshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.unibl.etf.ip.webshop.models.entities.ProductEntity;
import org.unibl.etf.ip.webshop.models.entities.ProductHasCategoryEntity;
import org.unibl.etf.ip.webshop.models.entities.UserEntity;

import java.util.List;

public interface ProductEntityRepository extends JpaRepository<ProductEntity, Integer> {
    List<ProductEntity> findAllByUserId(Integer id);

    @Query(value = "SELECT p FROM ProductEntity p WHERE (p.title LIKE ?1 OR p.description LIKE ?1 OR p.location LIKE ?1 OR p.contact LIKE ?1)")
    List<ProductEntity> findByKeyword(String keyword);

    @Query(value = "SELECT p FROM ProductEntity p WHERE p.used = :used")
    List<ProductEntity> findByCondition(boolean used);

    @Modifying
    @Query(value = "DELETE FROM ProductEntity p WHERE  p.id = :productId")
    void deleteByIdCustom(Integer productId);
}
