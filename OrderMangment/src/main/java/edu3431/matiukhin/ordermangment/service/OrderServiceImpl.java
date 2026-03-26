package edu3431.matiukhin.ordermangment.service;



import dto.*;

import edu3431.matiukhin.ordermangment.exeption.ElementNotFoundInBaseExeption;
import edu3431.matiukhin.ordermangment.mapper.OrderItemMapper;
import edu3431.matiukhin.ordermangment.mapper.OrderMapper;
import edu3431.matiukhin.ordermangment.model.OrderModel;
import edu3431.matiukhin.ordermangment.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.hibernate.query.Order;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    OrderRepository orderRepository;
    private final RestTemplate restTemplate;
    private final OrderMapper orderMapper;
    @Override
    public List<OrderDTO> getAllOrders() {

        List<OrderModel> orders = orderRepository.findAll();
        List<Long> clientIds = orders.stream().map(o -> o.getClientId()).distinct().collect(Collectors.toList());
        Map <Long, String> names = fetchClientNames(clientIds);
        return orders.stream().map(o -> {
            List<OrderItemDTO> items = o.getItems().stream()
                    .map(OrderItemMapper::toOrderItemDTO).collect(Collectors.toList());
            return orderMapper.toOrderDTO(o, names.getOrDefault(o.getClientId(), "Unknown"), items);
        }).collect(Collectors.toList());

    }

    private String fetchClientName(Long clientId) {
        return fetchClientNames(List.of(clientId))
                .getOrDefault(clientId, "Unknown");
    }


    private Map<Long, String> fetchClientNames(List<Long> clientIds) {
        String url = "http://CLIENTMANAGMENT/api/v1/clients/names";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<List<Long>> request = new HttpEntity<>(clientIds, headers);
        ResponseEntity<Map<Long, String>> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<Map<Long, String>>() {}
        );
        return response.getBody() != null ? response.getBody() : Collections.emptyMap();
    }


    /*private String fetchClientName(Long clientId) {
        try {
            ClientDTO client = restTemplate.getForObject(
                    "http://CLIENTMANAGMENT/api/v1/clients/id/" + clientId, ClientDTO.class);
            return client.getFirstName() + " " + client.getLastName();
        } catch (Exception e) {
            return "Unknown";
        }
    }*/



    @Override
    public List<OrderDTO> getOrdersByClientId(Long clientId) {

        List<OrderModel> orders = orderRepository.findAllByClientId(clientId);
        String clientName = fetchClientName(clientId);
        return orders.stream().map(o -> {
            List<OrderItemDTO> items = o.getItems().stream()
                    .map(OrderItemMapper::toOrderItemDTO)
                    .collect(Collectors.toList());
            return orderMapper.toOrderDTO(o,clientName,items);
        }).collect(Collectors.toList());
    }
    @Override
    public void saveNewOrder(SaveOrderDTO order){
        OrderModel orderModel = orderMapper.toOrder(order);

        orderRepository.save(orderModel);
    }

    @Override
    public void deleteOrder(Long order_id) {
        orderRepository.deleteById(order_id);
    }
    @Override
    public Map<Long, List<OrderDTO>> getOrdersByClientIds(List<Long> clientIds) {
        List<OrderModel> orders = orderRepository.findByClientIdIn(clientIds);

        return orders.stream()
                .collect(Collectors.groupingBy(
                        OrderModel::getClientId,
                        Collectors.mapping(order -> {
                            List<OrderItemDTO> orderItemDTOs = order.getItems().stream()
                                    .map(OrderItemMapper::toOrderItemDTO)
                                    .collect(Collectors.toList());
                            return orderMapper.toOrderDTO(order, null, orderItemDTOs);
                        }, Collectors.toList())
                ));
    }


    @Override
    @Transactional
    public void changeStatus(Long orderId, Status status) {
        OrderModel order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ElementNotFoundInBaseExeption("Order with ID " + orderId + " not found"));

        if (order.getStatus() != status) {
            order.setStatus(status);
            orderRepository.save(order);
        }
    }
}
