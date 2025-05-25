package dto;/*
@author sasha
@project microservices-shop
@class SaveOrderItemDTO
@version 1.0.0
@since 26.04.2025 - 16 - 21
*/



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SaveOrderItemDTO {
    private String productName;
    private int count;
    private Double totalPrice;
    private Long productId;
}

