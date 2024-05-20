package org.unibl.etf.ip.webshop.models.entities;

import lombok.*;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Data
public class ProductHasAttributeEntityPK implements Serializable {
    @Column(name = "product_id_product", nullable = false)
    @Id
    private Integer productId;

    @Column(name = "attribute_id_attribute", nullable = false)
    @Id
    private Integer attributeId;

}
