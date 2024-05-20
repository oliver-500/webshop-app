package org.unibl.etf.ip.webshop.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.unibl.etf.ip.webshop.models.enums.Role;

import java.util.Collection;
import java.util.List;

@Data
public class UserDTO implements UserDetails {

    private Integer id;
    private String city;
    private String avatar;
    private String email;
    private Boolean active;
    private String name;
    private String lastName;
    private String username;
    private String password;
    private Role type;




    @JsonProperty
    public Boolean getActive() {
        return active;
    }

    @JsonIgnore
    public void setActive(Boolean active) {
        this.active = active;
    }


    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(type.name()));
    }


    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }



    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

}
