package edu3431.matiukhin.productmanagment.mapper;


import dto.ProductDTO;
import dto.SaveProductDTO;
import edu3431.matiukhin.productmanagment.model.ProductModel;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductDTO toProductDTO(ProductModel product){
        return new ProductDTO(product.getId(),product.getName(), product.getCategory(), product.getType(), product.getPrice(),product.getCount(), product.getStatus());
    }


    public  ProductModel toProduct(SaveProductDTO product){
        return new ProductModel(product.getName(), product.getCategory(),product.getType(),product.getPrice(),product.getCount());
    }






}
