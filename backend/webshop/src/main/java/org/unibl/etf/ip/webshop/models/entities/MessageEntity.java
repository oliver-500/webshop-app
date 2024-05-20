package org.unibl.etf.ip.webshop.models.entities;

import lombok.*;

import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name = "message")
public class MessageEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_message", nullable = false)
    private Integer idMessage;

    @Basic
    @Column(name = "mcontent", nullable = false, length = 500)
    private String mcontent;

    @Basic
    @Column(name = "mread", nullable = true)
    private Boolean mread;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_user", referencedColumnName = "id_user")
    private UserEntity user;


}
