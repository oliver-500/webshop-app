package org.unibl.etf.ip.webshop.models.requests;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdatePasswordRequest {
    @Size(min = 5, max = 20, message = "Error")
    private String newPassword;
    @Size(min = 5, max = 20, message = "Error")
    private String oldPassword;
}
