package org.unibl.etf.ip.webshop.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.unibl.etf.ip.webshop.models.dto.ProductAttributeDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name = "product")
public class ProductEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_product", nullable = false)
    private Integer id;

    @Basic
    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Basic
    @Column(name = "description", nullable = true, length = 500)
    private String description;

    @Basic
    @Column(name = "location", nullable = false, length = 100)
    private String location;

    @Basic
    @Column(name = "used", nullable = false)
    private Boolean used;

    @Basic
    @Column(name = "price", nullable = true, precision = 2)
    private BigDecimal price;

    @Basic
    @Column(name = "contact", nullable = false, length = 15)
    private String contact;

    @Basic
    @Column(name = "sold", nullable = false)
    private Boolean sold;


    @JsonIgnore
    @OneToMany(mappedBy = "product",fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<ImageEntity> images;


    @ManyToOne(fetch = FetchType.EAGER)
    @ToString.Exclude
    @JoinColumn(name = "id_seller", referencedColumnName = "id_user")
    private UserEntity user;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ToString.Exclude
    @JoinTable(name = "product_has_attribute",
            joinColumns = @JoinColumn(name = "product_id_product"),
            inverseJoinColumns = @JoinColumn(name = "attribute_id_attribute")
    )
    private List<AttributeEntity> productAttributes;
    @ToString.Exclude
    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)

    @JoinTable(name = "product_has_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<CategoryEntity> productCategories;

}
