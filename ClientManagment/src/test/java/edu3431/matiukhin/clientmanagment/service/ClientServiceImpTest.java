package edu3431.matiukhin.clientmanagment.service;

import edu3431.matiukhin.clientmanagment.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ClientServiceImpTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImp clientService;

    @Test
    void deleteClient_ShouldCallRepositoryDeleteByEmail() {
        // Arrange
        String email = "test@example.com";

        // Act
        clientService.deleteClient(email);

        // Assert
        verify(clientRepository).deleteClientByEmail(email);
    }
}
