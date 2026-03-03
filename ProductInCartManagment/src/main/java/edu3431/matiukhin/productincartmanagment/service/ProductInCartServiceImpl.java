package edu3431.matiukhin.productincartmanagment.service;

import dto.*;
import edu3431.matiukhin.productincartmanagment.exeption.ElementNotFoundInBaseExeption;
import edu3431.matiukhin.productincartmanagment.mapper.ProductInCartMapper;
import edu3431.matiukhin.productincartmanagment.model.ProductInCartModel;
import edu3431.matiukhin.productincartmanagment.repository.ProductInCartRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ProductInCartServiceImpl implements ProductInCartService {
    private final ProductInCartRepository productInCartRepository;
    private final RestTemplate restTemplate;
    private final ProductInCartMapper productInCartMapper;



    @Override
    public void addItemToCart(Long productId, Long clientId, int quantity) {

        String urlPorducts = "http://productmanagment/api/v1/products/" + productId;
        ProductDTO productDTO = restTemplate.getForObject(urlPorducts, ProductDTO.class);
        if (productDTO == null) {
            throw new RuntimeException("Product not found with id: " + productId);
        }

        String urlClientById = "http://clientmanagment/api/v1/clients/id/" + clientId;
        ClientDTO clientDTO = restTemplate.getForObject(urlClientById, ClientDTO.class);
        if (clientDTO == null) {
            throw new RuntimeException("Client not found with id: " + clientId);
        }

        UpdateProductDTO updateProductDTO = new UpdateProductDTO(productDTO.getId(), productDTO.getName(), productDTO.getCategory(), productDTO.getType(), productDTO.getPrice(), productDTO.getCount());

        ProductInCartModel productInCartSameType = productInCartRepository.findByProductIdAndClientId(productId, clientId);

        if (productDTO.getCount() >= quantity && quantity > 0) {
            Double totalPrice = productDTO.getPrice() * quantity;
            int result = updateProductDTO.getCount() - quantity;
            updateProductDTO.setCount(result);

            String urlUpdateProduct = "http://productmanagment/api/v1/products";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            HttpEntity<UpdateProductDTO> entity = new HttpEntity<>(updateProductDTO, headers);
            restTemplate.exchange(urlUpdateProduct, HttpMethod.PUT, entity, Void.class);

            if (productInCartSameType != null) {
                productInCartSameType.setCount(productInCartSameType.getCount() + quantity);
                productInCartSameType.setTotalPrice(productInCartSameType.getTotalPrice() + totalPrice);
                productInCartRepository.save(productInCartSameType);
            } else {
                ProductInCartModel productInCart = new ProductInCartModel(productDTO.getName(), quantity, totalPrice, clientId, productId);
                productInCartRepository.save(productInCart);
            }
        } else {
            throw new RuntimeException("There is no product with this count");
        }
    }


    public List<ProductInCartDTO> getAllProductsInCart() {
        return productInCartRepository.findAll().stream()
                .map(product -> {
                    String clientName = fetchClientName(product.getClientId());
                    return productInCartMapper.toProductInCartDTO(product, clientName);
                }).collect(Collectors.toList());

    }
    private String fetchClientName(Long clientId) {
        try {
            ClientDTO client = restTemplate.getForObject(
                    "http://CLIENTMANAGMENT/api/v1/clients/id/" + clientId, ClientDTO.class);
            return client.getFirstName() + " " + client.getLastName();
        } catch (Exception e) {
            return "Unknown";
        }
    }
    @Override
    public void deleteItemFromCart(Long cartElementId) {
        productInCartRepository.deleteById(cartElementId);

    }

    @Override
    @Transactional
    public void placeOrder(Long clientId, String deliveryAddress) {

        List<ProductInCartModel> productsInCartOfClient = productInCartRepository.findAllByClientId(clientId);

        if (productsInCartOfClient.size() > 0) {
            SaveOrderDTO saveOrderDTO = new SaveOrderDTO();
            List<SaveOrderItemDTO> orderItems = new ArrayList<>();

            double totalPrice = 0;
            for (ProductInCartModel productInCart : productsInCartOfClient) {
                totalPrice += productInCart.getTotalPrice();
                SaveOrderItemDTO orderItem = new SaveOrderItemDTO(productInCart.getProductName(),
                        productInCart.getCount(),
                        productInCart.getTotalPrice(),
                        productInCart.getProductId());

                orderItems.add(orderItem);
                productInCartRepository.deleteById(productInCart.getId());
            }

            saveOrderDTO.setDeliveryAddress(deliveryAddress);
            saveOrderDTO.setTotalPrice(totalPrice);
            saveOrderDTO.setClientId(clientId);
            saveOrderDTO.setItems(orderItems);

            String url = "http://ordermanagment/api/v1/orders/save_order";
            restTemplate.postForObject(url, saveOrderDTO, OrderDTO.class);
        } else {
            throw new ElementNotFoundInBaseExeption("There is no product in cart of this client");
        }
    }

    @Override
   @Transactional
   public List<ProductInCartDTO>  getProductsByClientId(Long clientId){
        return productInCartRepository.findAllByClientId(clientId).stream()
                .map(product -> {
                    String clientName = fetchClientName(product.getClientId());
                    return productInCartMapper.toProductInCartDTO(product, clientName);
                }).collect(Collectors.toList());
    }
}
