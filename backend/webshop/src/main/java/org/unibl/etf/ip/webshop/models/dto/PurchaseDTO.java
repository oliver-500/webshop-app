package org.unibl.etf.ip.webshop.models.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.unibl.etf.ip.webshop.models.enums.PayingMethod;

@Data
public class PurchaseDTO {

    @Digits(integer = 10, fraction = 0, message = "Error")
    private Integer idPurchase;


    private PayingMethod payingMethod;

    @Size(min = 1, max = 100, message = "Error")
    private String payingInfo;

    @Digits(integer = 10, fraction = 0, message = "Error")
    private Integer productId;


    private Integer traderId;
}
