package org.unibl.etf.ip.webshop.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import jakarta.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Data
@Entity
@Table(name = "image")
public class ImageEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_image", nullable = false)
    private Integer id;

    @Basic
    @Column(name = "data", nullable = false)
    private byte[] data;

    @Basic
    @Column(name = "name", nullable = true, length = 45)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @ToString.Exclude
    @JoinColumn(name = "product_id") // Name of the foreign key column
    private ProductEntity product;



}
