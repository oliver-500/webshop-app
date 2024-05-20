package org.unibl.etf.ip.webshop.models.requests;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogsRequest {

    @Digits(integer = 10, fraction = 0, message = "Error")
    private Integer position;

    @Digits(integer = 10, fraction = 0, message = "Error")
    private Integer size;
}
