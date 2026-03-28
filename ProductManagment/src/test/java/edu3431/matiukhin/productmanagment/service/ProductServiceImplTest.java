package edu3431.matiukhin.productmanagment.service;

import dto.ProductDTO;
import edu3431.matiukhin.productmanagment.mapper.ProductMapper;
import edu3431.matiukhin.productmanagment.model.ProductModel;
import edu3431.matiukhin.productmanagment.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void getProductById_ShouldReturnProduct() {
        // Arrange
        Long productId = 10L;
        ProductModel mockModel = new ProductModel();
        mockModel.setId(productId);
        mockModel.setName("Test Laptop");

        ProductDTO mockDTO = new ProductDTO( "Test Laptop", "Electronics", "Laptop", 999.99, 5);
        mockDTO.setId(productId);

        when(productRepository.findById(productId)).thenReturn(Optional.of(mockModel));
        when(productMapper.toProductDTO(mockModel)).thenReturn(mockDTO);

        // Act
        ProductDTO result = productService.getProductById(productId);

        // Assert
        assertEquals("Test Laptop", result.getName());
        verify(productRepository).findById(productId);
    }
}
