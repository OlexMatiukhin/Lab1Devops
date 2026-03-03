package edu3431.matiukhin.productincartmanagment.controller;


import dto.AddProductToCartDTO;
import dto.BuyProductsFromCartDTO;
import dto.ProductInCartDTO;
import edu3431.matiukhin.productincartmanagment.service.ProductInCartService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RefreshScope
@RequestMapping("/api/v1/productsInCart")
@AllArgsConstructor
public class ProductInCartController {

    private final ProductInCartService productInCartService;


    @GetMapping
    public List<ProductInCartDTO> getAllProductsInCart() {
        return productInCartService.getAllProductsInCart();

    }


    @PostMapping("/add_product")
    public void addItemToCart( @RequestBody AddProductToCartDTO addProductToCartDTO) {
        productInCartService.addItemToCart(addProductToCartDTO.getProductId(), addProductToCartDTO.getClientId(), addProductToCartDTO.getQuantity());
    }


    @GetMapping("{clientId}")
    public List<ProductInCartDTO> getProductsByClientId(@PathVariable () Long clientId) {
       return productInCartService.getProductsByClientId(clientId);
    }

    @Transactional
    @PostMapping("/buy_from_cart")
    public void buyProduct(@Valid @RequestBody BuyProductsFromCartDTO buyProductFromCartDTO) {
        productInCartService.placeOrder(buyProductFromCartDTO.getClientId(), buyProductFromCartDTO.getAdress());
    }
}
