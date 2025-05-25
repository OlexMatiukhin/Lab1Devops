package edu3431.matiukhin.productmanagment.controller;



import dto.AddProductsDTO;
import dto.ProductDTO;
import dto.SaveProductDTO;
import dto.UpdateProductDTO;
import edu3431.matiukhin.productmanagment.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;



    @GetMapping
    public List<ProductDTO> getAllProductsInCart() {
       return productService.getAllProducts();
    }
    @PostMapping
    public void addNewProduct(@Valid @RequestBody SaveProductDTO product) {
         try {
             productService.addNewProduct(product);
         }catch (Exception e) {
             e.printStackTrace();
         }

    }

    @PutMapping("/add_count")
    public void addProducts( @Valid @RequestBody AddProductsDTO products) {
        productService.addProducts(products);
    }

    @PutMapping
    public void updateProduct  ( @RequestBody UpdateProductDTO product) {
        productService.updateProduct(product);
    }



    @GetMapping("/{productId}")
    public ProductDTO getProductById(@PathVariable("productId") Long id) {
       return productService.getProductById(id);
    }

    @DeleteMapping("{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct (@PathVariable("productId") Long productId) {
        productService.deleteProduct(productId);
    }




}
