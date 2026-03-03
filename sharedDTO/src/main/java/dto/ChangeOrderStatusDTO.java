package dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeOrderStatusDTO {
    @NotNull
     private Status orderStatus;
    @NotNull
    private Long orderId;
}


/* JSON шаблон

{
"orderStatus": "ACTIVE",
"adress": "Вулиця будівників 3, буд. 25, кв 12 ",
"clientId": 1,
};


*/

