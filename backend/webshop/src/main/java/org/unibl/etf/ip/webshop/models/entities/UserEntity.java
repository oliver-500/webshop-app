package org.unibl.etf.ip.webshop.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import jakarta.persistence.*;
import org.unibl.etf.ip.webshop.models.enums.Role;

import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_user", nullable = false)
    private Integer id;

    @Basic
    @Column(name = "name", nullable = false, length = 45)
    private String name;

    @Basic
    @Column(name = "last_name", nullable = false, length = 45)
    private String lastName;

    @Basic
    @Column(name = "username", nullable = false, length = 20)
    private String username;

    @Basic
    @Column(name = "password_hash", nullable = false, length = 45)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = true)
    private Role type;


    @Basic
    @Column(name = "city", nullable = true, length = 45)
    private String city;



    @Basic
    @Column(name = "email", nullable = true, length = 50)
    private String email;

    @Basic
    @Column(name = "active", nullable = true)
    private Boolean active;

    @Basic
    @Column(name = "avatar")
    private byte[] avatar;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<MessageEntity> message;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<ProductEntity> products;


    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<PurchaseEntity> purchases;


}
