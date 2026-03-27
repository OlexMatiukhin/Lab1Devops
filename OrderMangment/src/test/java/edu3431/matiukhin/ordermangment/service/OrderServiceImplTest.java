package edu3431.matiukhin.ordermangment.service;

import edu3431.matiukhin.ordermangment.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void deleteOrder_ShouldCallRepositoryDeleteById() {
        // Arrange
        Long orderId = 42L;

        // Act
        orderService.deleteOrder(orderId);

        // Assert
        verify(orderRepository).deleteById(orderId);
    }
}
