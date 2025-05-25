package edu3431.matiukhin.clientmanagment.service;




import dto.ClientDTO;
import dto.SaveClientDTO;
import dto.UpdateClientDTO;

import java.util.List;


public interface ClientService {
List<ClientDTO> getAllClients();
     void saveClient(SaveClientDTO saveclientDTO);
     ClientDTO findClientByEmail(String email);
     void updateClient(UpdateClientDTO clientDTO);
     ClientDTO findClientById(Long id);



    void deleteClient(String email);
}
