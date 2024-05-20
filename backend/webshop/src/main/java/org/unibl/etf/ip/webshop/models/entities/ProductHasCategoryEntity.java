package org.unibl.etf.ip.webshop.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_has_category")
@IdClass(ProductHasCategoryEntityPK.class)
public class ProductHasCategoryEntity {
    @Id
    @Column(name = "product_id", nullable = false)
    private Integer productId;

    @Id
    @Column(name = "category_id", nullable = false)
    private Integer categoryId;


}
