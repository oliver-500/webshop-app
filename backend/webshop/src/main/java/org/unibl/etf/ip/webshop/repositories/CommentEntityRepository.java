package org.unibl.etf.ip.webshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.ip.webshop.models.entities.CommentEntity;
import org.unibl.etf.ip.webshop.models.entities.ImageEntity;
import org.unibl.etf.ip.webshop.models.entities.MessageEntity;

import java.util.List;

public interface CommentEntityRepository extends JpaRepository<CommentEntity, Integer> {
    List<CommentEntity> getAllByProductId(Integer id);
}
