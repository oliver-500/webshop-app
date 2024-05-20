package org.unibl.etf.ip.webshop.models.requests;

import lombok.Data;

@Data
public class UpdateUserRequest {

    private String city;
    private String avatar;
    private String email;
    private String lastName;
    private String name;
    private String password;
}
