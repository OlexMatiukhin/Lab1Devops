package edu3431.matiukhin.productincartmanagment.service;

import dto.*;
import edu3431.matiukhin.productincartmanagment.exeption.ElementNotFoundInBaseExeption;
import edu3431.matiukhin.productincartmanagment.mapper.ProductInCartMapper;
import edu3431.matiukhin.productincartmanagment.model.ProductInCartModel;
import edu3431.matiukhin.productincartmanagment.repository.ProductInCartRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ProductInCartServiceImpl implements ProductInCartService {
    private final ProductInCartRepository productInCartRepository;
    private final RestTemplate restTemplate;
    private final ProductInCartMapper productInCartMapper;

    @Override
    public void addItemToCart(Long productId, Long clientId, int quantity) {

<<<<<<< HEAD
        String urlPorducts = "http://productmanagment/api/v1/products/" + productId;
        ProductDTO productDTO = restTemplate.getForObject(urlPorducts, ProductDTO.class);
        if (productDTO == null) {
            throw new RuntimeException("Product not found with id: " + productId);
        }
        //String urlClientById = "http://clientmanagment/api/v1/clients/id/" + clientId;
        //ClientDTO clientDTO = restTemplate.getForObject(urlClientById, ClientDTO.class);
        /*if (clientDTO == null) {
            throw new RuntimeException("Client not found with id: " + clientId);
        }*/
        if (productDTO.getCount() >= quantity && quantity > 0) {
            UpdateProductDTO updateProductDTO = new UpdateProductDTO(productDTO.getId(), productDTO.getName(), productDTO.getCategory(), productDTO.getType(), productDTO.getPrice(), productDTO.getCount());
            ProductInCartModel productInCartSameType = productInCartRepository.findByProductIdAndClientId(productId, clientId);
=======

            String urlPorducts = "http://productmanagment/api/v1/products/" + productId;
            ProductDTO productDTO = restTemplate.getForObject(urlPorducts, ProductDTO.class);
            System.out.println(productDTO);
            if (productDTO == null) {
                throw new RuntimeException("Product not found with id: " + productId);
            }


            String urlClientById = "http://clientmanagment/api/v1/clients/id/" + clientId;
            ClientDTO clientDTO = restTemplate.getForObject(urlClientById, ClientDTO.class);
            if (clientDTO == null) {
                throw new RuntimeException("Client not found with id: " + clientId);
            }



            UpdateProductDTO updateProductDTO = new UpdateProductDTO(productDTO.getId(),productDTO.getName(),productDTO.getCategory(),productDTO.getType(),productDTO.getPrice(),productDTO.getCount());
            System.out.println(updateProductDTO);

        ProductInCartModel productInCartSameType = productInCartRepository.findByProductIdAndClientId(productId, clientId);

        if (productDTO.getCount() >= quantity && quantity > 0 ) {
>>>>>>> ce92d58fb9c581b1b8206c89b2c21f6f98f5b812
            Double totalPrice = productDTO.getPrice() * quantity;
            int result = updateProductDTO.getCount() - quantity;
            updateProductDTO.setCount(result);
<<<<<<< HEAD
=======
            System.out.println(updateProductDTO);
            ObjectMapper mapper = new ObjectMapper();
            try {
                String json = mapper.writeValueAsString(updateProductDTO);
                System.out.println(json);
            }
            catch (JsonProcessingException e) {

            }
>>>>>>> ce92d58fb9c581b1b8206c89b2c21f6f98f5b812
            String urlUpdateProduct = "http://productmanagment/api/v1/products";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            if (productInCartSameType != null) {
                productInCartSameType.setCount(productInCartSameType.getCount() + quantity);
                productInCartSameType.setTotalPrice(productInCartSameType.getTotalPrice() + totalPrice);
                productInCartRepository.save(productInCartSameType);
            } else {
                ProductInCartModel productInCart = new ProductInCartModel(productDTO.getName(), quantity, totalPrice, clientId, productId);
                productInCartRepository.save(productInCart);
            }
            HttpEntity<UpdateProductDTO> entity = new HttpEntity<>(updateProductDTO, headers);
            restTemplate.exchange(urlUpdateProduct, HttpMethod.PUT, entity, Void.class);

        } else {
            throw new RuntimeException("There is no product with this count");
        }

    }


    public List<ProductInCartDTO> getAllProductsInCart() {
        List<ProductInCartModel> items = productInCartRepository.findAll();
        List<Long> clientIds = items.stream().
                map(ProductInCartModel::getClientId).distinct().collect(Collectors.toList());

        Map<Long, String> names = fetchClientNames(clientIds);
        return items.stream().map(p-> productInCartMapper.toProductInCartDTO(p,names.getOrDefault(p.getClientId(), "Unknown"))).collect(Collectors.toList());
    }
    /*private String fetchClientName(Long clientId) {
        try {
            ClientDTO client = restTemplate.getForObject(
                    "http://CLIENTMANAGMENT/api/v1/clients/id/" + clientId, ClientDTO.class);
            return client.getFirstName() + " " + client.getLastName();
        } catch (Exception e) {
            return "Unknown";
        }
    }*/
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
<<<<<<< HEAD
            String url = "http://ordermanagment/api/v1/orders/save_order";
            restTemplate.postForObject(url, saveOrderDTO, OrderDTO.class);
        } else {
=======
            System.out.println();

            String url = "http://ordermanagment/api/v1/orders/save_order";

            System.out.println(saveOrderDTO);

            restTemplate.postForObject(url,saveOrderDTO, OrderDTO.class);



        }
        else{
>>>>>>> ce92d58fb9c581b1b8206c89b2c21f6f98f5b812
            throw new ElementNotFoundInBaseExeption("There is no product in cart of this client");
        }
    }

    @Override
   @Transactional
   public List<ProductInCartDTO>  getProductsByClientId(Long clientId){
        List<ProductInCartModel> items = productInCartRepository.findAllByClientId(clientId);
        String clientName = fetchClientName(clientId);
        return items.stream()
                .map(p -> productInCartMapper.toProductInCartDTO(p, clientName ))
                .collect(Collectors.toList());
    }
    private String fetchClientName(Long clientId) {
        return fetchClientNames(List.of(clientId))
                .getOrDefault(clientId, "Unknown");
    }
    private Map<Long, String> fetchClientNames(List<Long> clientIds) {
        String url = "http://CLIENTMANAGMENT/api/v1/clients/names";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<List<Long>> request = new HttpEntity<>(clientIds, headers);
        ResponseEntity<Map<Long, String>> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<Map<Long, String>>() {}
        );
        return response.getBody() != null ? response.getBody() : Collections.emptyMap();
    }




}
