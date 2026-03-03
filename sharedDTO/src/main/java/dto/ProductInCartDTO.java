package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ProductInCartDTO {
    private Long id;
    private String productName;
    private int count;
    private Double totalPrice;
    private String clientName;
}

