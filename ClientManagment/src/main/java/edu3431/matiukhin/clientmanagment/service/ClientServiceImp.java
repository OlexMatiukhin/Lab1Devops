package edu3431.matiukhin.clientmanagment.service;


import dto.ClientDTO;
import dto.SaveClientDTO;
import dto.UpdateClientDTO;
import edu3431.matiukhin.clientmanagment.exeption.ElementNotFoundInBaseExeption;
import edu3431.matiukhin.clientmanagment.mapper.ClientMapper;
import edu3431.matiukhin.clientmanagment.model.ClientModel;
import edu3431.matiukhin.clientmanagment.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClientServiceImp implements ClientService {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Override
    public List<ClientDTO> getAllClients() {

        return clientRepository.findAll().stream().map(clientMapper::toClientDTO).collect(Collectors.toList());
    }

    @Override
    public void saveClient(SaveClientDTO saveclientDTO) {
        ClientModel client = clientMapper.toClient(saveclientDTO);
         clientRepository.save(client);
    }
    @Override
    public ClientDTO findClientByEmail(String email) {
        ClientModel client = clientRepository.findClientByEmail(email);

        if (client == null) {
            throw new ElementNotFoundInBaseExeption("Client with email " + email + " not found");
        }

        return clientMapper.toClientDTO(client);
    }


    @Override
    public void updateClient(UpdateClientDTO clientDTO) {
        ClientModel client = clientRepository.findById(clientDTO.getId())
                .orElseThrow(() -> new ElementNotFoundInBaseExeption("Client for updating not found"));


        client.setFirstName(clientDTO.getFirstName());
        client.setLastName(clientDTO.getLastName());
        client.setBirthDate(clientDTO.getBirthDate());
        clientRepository.save(client);
    }

    @Override
    public ClientDTO findClientById(Long id) {

        ClientModel client = clientRepository.findById(id).orElseThrow(() -> new ElementNotFoundInBaseExeption("Client  not found"));
        return clientMapper.toClientDTO(client);

    }

    @Override
    public void deleteClient(String email) {
        clientRepository.deleteClientByEmail(email);
    }
}
