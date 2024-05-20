package org.unibl.etf.ip.webshop.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name = "category")
public class CategoryEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_category", nullable = false)
    private Integer id;

    @Basic
    @Column(name = "name", nullable = false, length = 45)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "category")
    private List<AttributeEntity> attributes;

    @JsonIgnore
    @ManyToMany(mappedBy = "productCategories")
    List<ProductEntity> products;
}
