package org.unibl.etf.ip.webshop.models.entities;

import lombok.*;

import jakarta.persistence.*;
import org.unibl.etf.ip.webshop.models.enums.PayingMethod;

import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name = "purchase")
public class PurchaseEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_purchase", nullable = false)
    private Integer idPurchase;


    @Enumerated(EnumType.STRING)
    @Column(name = "paying_method", nullable = false)
    private PayingMethod payingMethod;

    @Basic
    @Column(name = "paying_info", nullable = true, length = 100)
    private String payingInfo;

    @OneToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id_product")
    private ProductEntity product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_buyer", referencedColumnName = "id_user")
    private UserEntity user;

}
