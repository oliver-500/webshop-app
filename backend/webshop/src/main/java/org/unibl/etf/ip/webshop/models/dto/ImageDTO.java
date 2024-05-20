package org.unibl.etf.ip.webshop.models.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.unibl.etf.ip.webshop.models.entities.ProductEntity;
@Data
public class ImageDTO {

    @Digits(integer = 10, fraction = 0, message = "Error")
    private Integer id;

    @Digits(integer = 10, fraction = 0, message = "Error")
    private Integer productId;
    @Size(min = 1, message = "Error")
    private String data;

    private String name;


}
