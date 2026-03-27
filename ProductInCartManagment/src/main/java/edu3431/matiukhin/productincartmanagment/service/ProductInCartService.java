package edu3431.matiukhin.productincartmanagment.service;
import dto.ProductInCartDTO;

import java.util.List;
import java.util.Map;

public interface ProductInCartService {
    void addItemToCart(Long productId, Long clientId, int quantity);
    void deleteItemFromCart(Long cartElementId);
    List<ProductInCartDTO> getAllProductsInCart();
    void placeOrder(Long clientId, String deliveryAddress);
    List<ProductInCartDTO> getProductsByClientId(Long clientId);
    Map<Long, List<ProductInCartDTO>> getProductsByClientIds(List<Long> clientIds);
}
