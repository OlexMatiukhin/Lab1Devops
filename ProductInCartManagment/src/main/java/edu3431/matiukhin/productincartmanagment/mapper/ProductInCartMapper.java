package edu3431.matiukhin.productincartmanagment.mapper;



import dto.ClientDTO;
import dto.ProductInCartDTO;
import edu3431.matiukhin.productincartmanagment.model.ProductInCartModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Component
public class ProductInCartMapper {

    private  final RestTemplate restTemplate;

    public ProductInCartDTO toProductInCartDTO(ProductInCartModel product) {

        System.out.println(product.getClientId());
        String urlProductById = "http://clientmanagment/api/v1/clients/id/" + product.getClientId();
         ClientDTO client = restTemplate.getForObject(urlProductById, ClientDTO.class);
        return new ProductInCartDTO(
                product.getId(),
                product.getProductName(),
                product.getCount(),
                product.getTotalPrice(),
                client.getFirstName() + " " + client.getLastName()
        );
    }
}






