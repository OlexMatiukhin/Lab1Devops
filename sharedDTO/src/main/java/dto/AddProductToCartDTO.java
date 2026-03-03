package dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddProductToCartDTO {
    @NotNull(message = "ID продукту не можу бути null")
    private Long productId;
    @Positive(message = "Кількість має бути більше нуля")
    private int quantity;
    private Long clientId;

}

/* JSON шаблон

{
"productId": 1,
"quantity": 3,
"clientId": 1,
};


*/