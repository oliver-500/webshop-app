package org.unibl.etf.ip.webshop.models.dto;

import jakarta.validation.constraints.Digits;
import lombok.Data;

@Data
public class ProductCategoryDTO {
    @Digits(integer = 10, fraction = 0, message = "Error")
    private int productId;
    @Digits(integer = 10, fraction = 0, message = "Error")
    private int categoryId;
}
