package org.unibl.etf.ip.webshop.models.dto;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryDTO {

    @Digits(integer = 10, fraction = 0, message = "Error")
    private Integer id;
    @Size(min = 1, max = 45, message = "Error")
    private String name;
}
