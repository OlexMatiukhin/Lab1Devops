package edu3431.matiukhin.productmanagment.service;


import dto.AddProductsDTO;
import dto.ProductDTO;
import dto.SaveProductDTO;
import dto.UpdateProductDTO;

import java.util.List;

public interface ProductService {




    List<ProductDTO> getAllProducts();
     void addNewProduct(SaveProductDTO productDTO) ;
     void addProducts(AddProductsDTO addProductsDTO);
     void updateProduct(UpdateProductDTO productDTO);
     void deleteProduct(Long id);
    ProductDTO getProductById( Long id);
}
