package org.unibl.etf.ip.webshop.models.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProductAttributeDTO {

    @Digits(integer = 10, fraction = 0, message = "Error")
    private int productId;
    @Digits(integer = 10, fraction = 0, message = "Error")
    private int attributeId;
    @Size(min = 1, max = 255, message = "Error")
    private String value;
}
