package dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClientDTO{
    private Long id;
    private String firstName;
    private String lastName;
    private String Email;
    private String Phone;
    private LocalDate birthDate;
    private int age;
    private List<OrderDTO> orders;
    private List<ProductInCartDTO> products;
}






