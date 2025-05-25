package dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class OrderDTO {

    private Long id;
    public Status status;

    private String clientName;
    private List<OrderItemDTO> orders;

}
