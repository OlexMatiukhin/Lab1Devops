package edu3431.matiukhin.clientmanagment.service;




import dto.ClientDTO;
import dto.SaveClientDTO;
import dto.UpdateClientDTO;

import java.util.List;
import java.util.Map;


public interface ClientService {
List<ClientDTO> getAllClients();
     void saveClient(SaveClientDTO saveclientDTO);
     ClientDTO findClientByEmail(String email);
     void updateClient(UpdateClientDTO clientDTO);
     ClientDTO findClientById(Long id);

    Map<Long, String> getClientNames(List<Long> ids);

    void deleteClient(String email);
}
