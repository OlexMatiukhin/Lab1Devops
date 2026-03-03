package dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateProductDTO {
    @NotNull
    Long id;
    @NotNull
    String name;
    @NotNull
    String category;
    @NotNull
    String type;
    @Positive
    Double price;
    @Positive
    int count;
}


/* JSON шаблон

{
"id": 1,
"name": "ASUS ROG",
"category": "Комп'ютерна переферія",
"type": "Монітори",
"price": 3000.
"count": 4
};


*/

