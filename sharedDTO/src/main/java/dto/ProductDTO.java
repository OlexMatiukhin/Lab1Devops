package dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ProductDTO {
    private Long id;
    private String name;
    private String category;
    private String type;
    private Double price;
    private int count;
    private String status;

    public ProductDTO(String name, String category, String type, Double price, int count) {
        this.name = name;
        this.category = category;
        this.type = type;
        this.price = price;
        this.count = count;
    }
}






