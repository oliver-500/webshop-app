package org.unibl.etf.ip.webshop.models.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "comment")
public class CommentEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_comment", nullable = false)
    private Integer id;

    @Basic
    @Column(name = "username", nullable = true, length = 20)
    private String username;

    @Basic
    @Column(name = "mcontent", nullable = false, length = 300)
    private String mcontent;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id") // Name of the foreign key column
    private ProductEntity product;
}
