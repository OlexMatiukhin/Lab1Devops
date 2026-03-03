package edu3431.matiukhin.productincartmanagment.mapper;

import dto.ProductInCartDTO;
import edu3431.matiukhin.productincartmanagment.model.ProductInCartModel;
import org.springframework.stereotype.Component;

@Component
public class ProductInCartMapper {

    public ProductInCartDTO toProductInCartDTO(ProductInCartModel product, String clientFullName) {
        return new ProductInCartDTO(
                product.getId(),
                product.getProductName(),
                product.getCount(),
                product.getTotalPrice(),
                clientFullName
        );
    }
}
