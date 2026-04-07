package edu3431.matiukhin.clientmanagment.service;


import dto.*;
import edu3431.matiukhin.clientmanagment.exeption.ElementNotFoundInBaseExeption;
import edu3431.matiukhin.clientmanagment.mapper.ClientMapper;
import edu3431.matiukhin.clientmanagment.model.ClientModel;
import edu3431.matiukhin.clientmanagment.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClientServiceImp implements ClientService {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final RestTemplate restTemplate;

    @Override
    public List<ClientDTO> getAllClients() {
        List<ClientModel> clients = clientRepository.findAll();
        List<Long> clientIds = clients.stream().map(ClientModel::getId).collect(Collectors.toList());

        Map<Long, List<ProductInCartDTO>> cartMap = fetchAllProductsInCart(clientIds);
        Map<Long, List<OrderDTO>> ordersMap = fetchAllOrders(clientIds);

        return clients.stream().map(client -> {
            List<ProductInCartDTO> products = cartMap.getOrDefault(client.getId(), Collections.emptyList());
            List<OrderDTO> orders = ordersMap.getOrDefault(client.getId(), Collections.emptyList());
            return clientMapper.toClientDTO(client, products, orders);
        }).collect(Collectors.toList());
    }

    private Map<Long, List<ProductInCartDTO>> fetchAllProductsInCart(List<Long> clientIds) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<List<Long>> request = new HttpEntity<>(clientIds, headers);
            ResponseEntity<Map<Long, List<ProductInCartDTO>>> response = restTemplate.exchange(
                    "http://productincartmanagment:8082/api/v1/productsInCart/by-clients",
                    HttpMethod.POST, request,
                    new ParameterizedTypeReference<Map<Long, List<ProductInCartDTO>>>() {}
            );
            return response.getBody() != null ? response.getBody() : Collections.emptyMap();
        } catch (HttpClientErrorException | HttpServerErrorException | ResourceAccessException ex) {
            return Collections.emptyMap();
        }
    }

    private Map<Long, List<OrderDTO>> fetchAllOrders(List<Long> clientIds) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<List<Long>> request = new HttpEntity<>(clientIds, headers);
            ResponseEntity<Map<Long, List<OrderDTO>>> response = restTemplate.exchange(
                    "http://ordermanagment:8081/api/v1/orders/by-clients",
                    HttpMethod.POST, request,
                    new ParameterizedTypeReference<Map<Long, List<OrderDTO>>>() {}
            );
            return response.getBody() != null ? response.getBody() : Collections.emptyMap();
        } catch (HttpClientErrorException | HttpServerErrorException | ResourceAccessException ex) {
            return Collections.emptyMap();
        }
    }

    private List<ProductInCartDTO> fetchProductsInCart(Long clientId) {
        try {
            ResponseEntity<List<ProductInCartDTO>> response = restTemplate.exchange(
                    "http://productincartmanagment:8082/api/v1/productsInCart/" + clientId,
                    HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<ProductInCartDTO>>() {}
            );
            return response.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException | ResourceAccessException ex) {
            return null;
        }
    }

    private List<OrderDTO> fetchOrders(Long clientId) {
        try {
            ResponseEntity<List<OrderDTO>> response = restTemplate.exchange(
                    "http://ordermanagment:8081/api/v1/orders/" + clientId,
                    HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<OrderDTO>>() {});
            return response.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException | ResourceAccessException ex) {
            return null;
        }
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
        List<ProductInCartDTO> products = fetchProductsInCart(client.getId());
        List<OrderDTO> orders = fetchOrders(client.getId());
        return clientMapper.toClientDTO(client, products, orders);
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
        ClientModel client = clientRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundInBaseExeption("Client not found"));
        List<ProductInCartDTO> products = fetchProductsInCart(client.getId());
        List<OrderDTO> orders = fetchOrders(client.getId());
        return clientMapper.toClientDTO(client, products, orders);
    }

    @Override
    public void deleteClient(String email) {
        clientRepository.deleteClientByEmail(email);
    }

    @Override
    public Map<Long, String> getClientNames(List<Long> ids) {
        return clientRepository.findAllById(ids).stream()
                .collect(Collectors.toMap(ClientModel::getId,
                        client -> client.getFirstName() + " " + client.getLastName()));
    }
}
