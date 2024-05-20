package org.unibl.etf.ip.webshop.models.requests;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ActivationRequest {

    @Digits(integer = 10, fraction = 0, message = "Error")
    private String pin;
    @Size(min = 5, max = 45, message = "Error")
    private String username;
}
