package dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BuyProductsFromCartDTO {
    @NotNull

    private Long clientId;
    @NotNull
    private String adress;
}


/* JSON шаблон

{
"clientId": 1,
"adress": "Вулиця будівників 3, буд. 25, кв 12 ",
"clientId": 1,
};


*/
