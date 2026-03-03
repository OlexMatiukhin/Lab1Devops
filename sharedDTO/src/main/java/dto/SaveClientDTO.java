package dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@Data
@Builder
@ToString
@NoArgsConstructor
public class SaveClientDTO
{
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @Past

    private LocalDate birthDate;
    @Email
    private String email;

    @Pattern(regexp = "^\\+380\\d{9}$")
    private String phone;
}


/* JSON шаблон

{
"firstName": "Євген",
"lastName":"Мартиненко",
"birthDate": "1999-12-25",
"email": "skif@gmail.com",
"phone": "+380983248867"
};


*/


