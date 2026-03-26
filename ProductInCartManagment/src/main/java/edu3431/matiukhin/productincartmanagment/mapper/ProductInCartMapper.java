package edu3431.matiukhin.productincartmanagment.mapper;

import dto.ProductInCartDTO;
import edu3431.matiukhin.productincartmanagment.model.ProductInCartModel;
import org.springframework.stereotype.Component;

@Component
public class ProductInCartMapper {

<<<<<<< HEAD
    public ProductInCartDTO toProductInCartDTO(ProductInCartModel product, String clientFullName) {
=======
    private  final RestTemplate restTemplate;

    public ProductInCartDTO toProductInCartDTO(ProductInCartModel product) {

        System.out.println(product.getClientId());
        String urlProductById = "http://clientmanagment/api/v1/clients/id/" + product.getClientId();
         ClientDTO client = restTemplate.getForObject(urlProductById, ClientDTO.class);
>>>>>>> ce92d58fb9c581b1b8206c89b2c21f6f98f5b812
        return new ProductInCartDTO(
                product.getId(),
                product.getProductName(),
                product.getCount(),
                product.getTotalPrice(),
                clientFullName
        );
    }
}
