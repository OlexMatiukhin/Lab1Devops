package edu3431.matiukhin.productmanagment.service;

import dto.AddProductsDTO;
import dto.ProductDTO;
import dto.SaveProductDTO;
import dto.UpdateProductDTO;

import edu3431.matiukhin.productmanagment.exeption.CostExeption;
import edu3431.matiukhin.productmanagment.exeption.ElementNotFoundInBaseExeption;
import edu3431.matiukhin.productmanagment.mapper.ProductMapper;
import edu3431.matiukhin.productmanagment.model.ProductModel;
import edu3431.matiukhin.productmanagment.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    ProductRepository productRepository;
    private final ProductMapper productMapper;


    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream().map(productMapper::toProductDTO).collect(Collectors.toList());
    }
    @Transactional
    @Override
    public void addNewProduct(SaveProductDTO productDTO) {
        ProductModel productModel = productMapper.toProduct(productDTO);
        if(productModel.getCount()>=0){
            productRepository.save(productModel);
        }

    }
    @Transactional
    @Override
    public void addProducts(AddProductsDTO addProductsDTO){
        Long productId=addProductsDTO.getProductId();
        int count =addProductsDTO.getProductsCount();
        ProductModel product = productRepository.findById(productId)
                .orElseThrow(() -> new ElementNotFoundInBaseExeption("Product not found with id"+productId));
        if(count>0){
            product.setCount(product.getCount()+count);
            productRepository.save(product);
        }
        else{
            throw new CostExeption("Count must more then null!");
        }
    }

    @Transactional
    @Override
    public void updateProduct(UpdateProductDTO productDTO) {

        ProductModel product = productRepository.findById(productDTO.getId())
                .orElseThrow(() -> new ElementNotFoundInBaseExeption("Client not found"));



     product.setName(productDTO.getName());
     product.setCount(productDTO.getCount());
     product.setPrice(productDTO.getPrice());
     product.setCategory(productDTO.getCategory());
     product.setType(productDTO.getType());
     productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public ProductDTO getProductById(Long id) {

        ProductModel product = productRepository.findById(id).orElseThrow(() -> new ElementNotFoundInBaseExeption("Product not found"));
        return productMapper.toProductDTO(product);
    }
}
