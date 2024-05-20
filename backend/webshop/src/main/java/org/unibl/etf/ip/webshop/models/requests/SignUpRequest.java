package org.unibl.etf.ip.webshop.models.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignUpRequest {
    @Size(min = 5, max = 20, message = "Error")
    private String username;
    @Size(min = 5, max = 20, message = "Error")
    private String password;
    @Size(min = 5, max = 45, message = "Error")
    private String city;
    @Size(min = 5, max = 45, message = "Error")
    private String name;
    @Size(min = 5, max = 45, message = "Error")
    private String lastName;
    private String avatar;
    @Email(message = "Error")
    private String email;
}
