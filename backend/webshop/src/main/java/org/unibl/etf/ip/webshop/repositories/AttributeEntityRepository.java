package org.unibl.etf.ip.webshop.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.unibl.etf.ip.webshop.models.entities.AttributeEntity;

import java.util.List;

@Repository
public interface AttributeEntityRepository extends JpaRepository<AttributeEntity, Integer> {
    List<AttributeEntity> getAllByCategoryId(Integer id);
}
