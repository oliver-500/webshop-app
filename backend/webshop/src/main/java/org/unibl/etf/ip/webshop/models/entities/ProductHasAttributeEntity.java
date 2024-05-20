package org.unibl.etf.ip.webshop.models.entities;

import lombok.*;

import jakarta.persistence.*;
import java.util.Objects;

@Data
@Entity
@Table(name = "product_has_attribute")
@IdClass(ProductHasAttributeEntityPK.class)
public class ProductHasAttributeEntity {

    @Id
    @Column(name = "product_id_product", nullable = false)
    private Integer productId;

    @Id
    @Column(name = "attribute_id_attribute", nullable = false)
    private Integer attributeId;

    @Basic
    @Column(name = "value", nullable = false, length = 255)
    private String value;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @MapsId("id_product")
//    @JoinColumn(name = "product_id_product", referencedColumnName = "id_product", insertable = false, updatable = false)
//    private ProductEntity product;
//
//    @ManyToOne(fetch = FetchType.EAGER)
//    @MapsId("id_attribute")
//    @JoinColumn(name = "attribute_id_attribute", referencedColumnName = "id_attribute", insertable = false, updatable = false)
//    private AttributeEntity attribute;


//
//    @Id
//    @ManyToOne
//    @MapsId("productId") // Maps the "traderId" property in the composite primary key
//    @JoinColumn(name = "id_product") // Name of the foreign key column
//    private ProductEntity product;
//
//    @Id
//    @ManyToOne
//    @MapsId("attributeId") // Maps the "traderId" property in the composite primary key
//    @JoinColumn(name = "id_attribute") // Name of the foreign key column
//    private AttributeEntity attribute;



}
