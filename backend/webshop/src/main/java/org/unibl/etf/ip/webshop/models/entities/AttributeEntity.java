package org.unibl.etf.ip.webshop.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;

import java.util.List;

@Data
@Entity
@Table(name = "attribute")
public class AttributeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_attribute", nullable = false)
    private Integer id;

    @Basic
    @Column(name = "name", nullable = true, length = 45)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="category_id", referencedColumnName = "id_category", nullable = true)
    private CategoryEntity category;

//
//    @OneToMany(mappedBy = "attribute")
//    @JsonIgnore
//    private List<ProductHasAttributeEntity> productHasAttribute;


    @JsonIgnore
    @ManyToMany(mappedBy = "productAttributes", cascade=CascadeType.ALL)
    List<ProductEntity> products;

}
