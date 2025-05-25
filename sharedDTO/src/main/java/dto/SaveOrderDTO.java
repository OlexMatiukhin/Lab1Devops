package dto;/*
@author sasha
@project microservices-shop
@class SaveOrderDTO
@version 1.0.0
@since 26.04.2025 - 16 - 03
*/

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
@Data
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class SaveOrderDTO {
    private Double totalPrice;
    private String deliveryAddress;
    private Long clientId;
    private List<SaveOrderItemDTO> items;
}
