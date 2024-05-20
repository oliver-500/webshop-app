package org.unibl.etf.ip.webshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.unibl.etf.ip.webshop.models.dto.CategoryDTO;
import org.unibl.etf.ip.webshop.models.entities.CategoryEntity;
import org.unibl.etf.ip.webshop.models.entities.ProductEntity;

import java.util.List;

@Repository
public interface CategoryEntityRepository extends JpaRepository<CategoryEntity, Integer> {

}
