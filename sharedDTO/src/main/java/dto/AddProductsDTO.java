package dto;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor

public class AddProductsDTO {
    private Long productId;
    @Positive
    private int productsCount;
}

/* JSON шаблон

{
"productId": 1,
"productsCount": 3
};


* */

