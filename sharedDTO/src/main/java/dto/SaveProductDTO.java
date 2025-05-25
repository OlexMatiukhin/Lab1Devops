package dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SaveProductDTO{
    @NotNull
    private String name;
    @NotNull
    private String category;
    @NotNull
    private String type;
    @Positive
    private Double price;
    @Positive
    private int count;
}


/* JSON шаблон

{
"name": "ASUS ROG",
"category": "Комп'ютерна переферія",
"type": "Монітори",
"price": "3000",
"count": "4"
};


*/

