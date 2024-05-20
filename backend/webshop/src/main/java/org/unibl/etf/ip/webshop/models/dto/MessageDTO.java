package org.unibl.etf.ip.webshop.models.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MessageDTO {

    @Size(min = 1, max = 500, message = "Error")
    private String mcontent;

    @Digits(integer = 10, fraction = 0, message = "Error")
    private Integer userId;
}
