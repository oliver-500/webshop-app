package org.unibl.etf.ip.webshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.ip.webshop.models.entities.UserEntity;

public interface UserEntityRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity findByUsername(String username);
}
