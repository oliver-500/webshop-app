package org.unibl.etf.ip.webshop.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data

public class ProductHasCategoryEntityPK implements Serializable {
    @Column(name = "product_id", nullable = false)
    @Id
    private Integer productId;

    @Column(name = "category_id", nullable = false)
    @Id
    private Integer categoryId;


}
