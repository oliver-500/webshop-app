package org.unibl.etf.ip.webshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.unibl.etf.ip.webshop.models.entities.AttributeEntity;
import org.unibl.etf.ip.webshop.models.entities.ImageEntity;

import java.util.List;

public interface ImageEntityRepository extends JpaRepository<ImageEntity, Integer> {
    List<ImageEntity> getAllByProductId(Integer id);
    Integer deleteAllByProductId(Integer id);

    @Query("SELECT i FROM ImageEntity i WHERE i.product.id = ?1 ORDER BY i.id")
    List<ImageEntity> getOneByProductId(Integer id);
}
