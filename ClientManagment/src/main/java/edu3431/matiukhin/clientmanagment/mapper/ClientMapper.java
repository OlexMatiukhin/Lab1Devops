package edu3431.matiukhin.clientmanagment.mapper;


import dto.ClientDTO;
import dto.OrderDTO;
import dto.ProductInCartDTO;
import dto.SaveClientDTO;
import edu3431.matiukhin.clientmanagment.model.ClientModel;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ClientMapper {



private  final RestTemplate restTemplate;

    public ClientDTO toClientDTO(ClientModel client) {

       /* List<OrderDTO> orders = client.getOrderItems()
                .stream()
                .map(orderMapper::toOrderDTO) // Исправленный вызов метода
                .collect(Collectors.toList());
*/
        String urlProductById = "http://PRODUCTINCARTMANAGMENT/api/v1/productsInCart/" + client.getId();
        String ulrOrderId = "http://ORDERMANAGMENT/api/v1/orders/" + client.getId();

        List<ProductInCartDTO> products = new ArrayList<>();
        List<OrderDTO> orders = null;

        try {
            ResponseEntity<List<ProductInCartDTO>> responseProductsInCart = restTemplate.exchange(
                    urlProductById,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<ProductInCartDTO>>() {}
            );
            products = responseProductsInCart.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException | ResourceAccessException ex) {
            System.out.println("I am in catch when try to get products in cart");
            System.out.println(ex.getMessage());
            products = null;
        }

        try {
            ResponseEntity<List<OrderDTO>> responseOrders = restTemplate.exchange(
                    ulrOrderId,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<OrderDTO>>() {}
            );
            orders = responseOrders.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException | ResourceAccessException ex) {

            orders = null;
        }
        return new ClientDTO(client.getId(), client.getFirstName(), client.getLastName(), client.getEmail(),
                client.getPhone(), client.getBirthDate(), client.getAge(), orders, products);
    }

    public ClientModel toClient(SaveClientDTO clientDTO) {
        return new ClientModel(clientDTO.getFirstName(), clientDTO.getLastName(), clientDTO.getEmail(),
                clientDTO.getPhone(), clientDTO.getBirthDate());
    }



}
