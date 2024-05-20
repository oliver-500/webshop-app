package org.unibl.etf.ip.webshop.models.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDTO {
    @Size(min = 1, max = 300, message = "Error")
    private String mcontent;

    @Size(min = 5, max = 20, message = "Error")
    private String username;

    @Digits(integer = 10, fraction = 0, message = "Error")
    private Integer productId;
}
