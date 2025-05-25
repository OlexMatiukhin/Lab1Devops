package dto;/*
@author sasha
@project springshop
@class OrderItemDTO
@version 1.0.0
@since 22.03.2025 - 20 - 40
*/

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class OrderItemDTO {
    private Long id;
    private String productName;
    private int count;
    private Double totalPrice;

}
