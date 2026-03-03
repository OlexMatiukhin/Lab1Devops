package edu3431.matiukhin.productincartmanagment.service;
import dto.ProductInCartDTO;

import java.util.List;

public interface ProductInCartService {
    //public void addItemToCart(Long itemId, int quantity);
    void addItemToCart(Long productId, Long clientId, int quantity);
    void deleteItemFromCart(Long cartElementId);
    List<ProductInCartDTO> getAllProductsInCart();
    void placeOrder(Long clientId, String deliveryAddress);
    List<ProductInCartDTO> getProductsByClientId(Long clientId);
}
