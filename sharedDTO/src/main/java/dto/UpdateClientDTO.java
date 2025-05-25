package dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UpdateClientDTO{
    @NotNull
    private Long id;
    @NotNull
    private  String firstName;
    @NotNull
    private   String lastName;
    @Past
    private  java.time.LocalDate birthDate;
}

/* JSON шаблон

{
"id":3,
"firstName": "Євген",
"lastName":"Мартиненко",
"birthDate": "1999-12-25",
"email": "skif@gmail.com",
"phone": "+380983248867"
};


*/


