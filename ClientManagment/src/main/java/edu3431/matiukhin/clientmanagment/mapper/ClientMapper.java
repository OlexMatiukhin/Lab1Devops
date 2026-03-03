package edu3431.matiukhin.clientmanagment.mapper;
import dto.ClientDTO;
import dto.OrderDTO;
import dto.ProductInCartDTO;
import dto.SaveClientDTO;
import edu3431.matiukhin.clientmanagment.model.ClientModel;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class ClientMapper {
    public ClientDTO toClientDTO(ClientModel client, List<ProductInCartDTO> products, List<OrderDTO> orders) {
        return new ClientDTO(client.getId(), client.getFirstName(), client.getLastName(), client.getEmail(),
                client.getPhone(), client.getBirthDate(), client.getAge(), orders, products);
    }

    public ClientModel toClient(SaveClientDTO clientDTO) {
        return new ClientModel(clientDTO.getFirstName(), clientDTO.getLastName(), clientDTO.getEmail(),
                clientDTO.getPhone(), clientDTO.getBirthDate());
    }
}
