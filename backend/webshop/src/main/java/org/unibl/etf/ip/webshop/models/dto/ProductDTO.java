package org.unibl.etf.ip.webshop.models.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductDTO {

    @Digits(integer = 10, fraction = 0, message = "Error")
    private Integer id;

    @Size(min = 1, max = 50, message = "Error")
    private String title;

    @Size(min = 1, max = 500, message = "Error")
    private String description;

    @Size(min = 1, max = 100, message = "Error")
    private String location;


    private Boolean used;


    private BigDecimal price;


    @Size(min = 1, max = 15, message = "Error")
    private String contact;


    private Boolean sold;



    @Digits(integer = 10, fraction = 0, message = "Error")
    private Integer sellerId;

    private List<ProductAttributeDTO> attributes;

    private List<CategoryDTO> categories;
}
