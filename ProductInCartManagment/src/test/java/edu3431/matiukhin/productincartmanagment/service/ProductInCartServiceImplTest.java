package edu3431.matiukhin.productincartmanagment.service;

import edu3431.matiukhin.productincartmanagment.repository.ProductInCartRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProductInCartServiceImplTest {

    @Mock
    private ProductInCartRepository repository;

    @InjectMocks
    private ProductInCartServiceImpl service;

    @Test
    void deleteItemFromCart_ShouldCallRepositoryDeleteById() {
        // Arrange
        Long cartItemId = 99L;

        // Act
        service.deleteItemFromCart(cartItemId);

        // Assert
        verify(repository).deleteById(cartItemId);
    }
}
