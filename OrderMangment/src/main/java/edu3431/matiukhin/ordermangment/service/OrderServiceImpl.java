package edu3431.matiukhin.ordermangment.service;



import dto.*;

import edu3431.matiukhin.ordermangment.exeption.ElementNotFoundInBaseExeption;
import edu3431.matiukhin.ordermangment.mapper.OrderItemMapper;
import edu3431.matiukhin.ordermangment.mapper.OrderMapper;
import edu3431.matiukhin.ordermangment.model.OrderModel;
import edu3431.matiukhin.ordermangment.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    OrderRepository orderRepository;
    private final RestTemplate restTemplate;
    private final OrderMapper orderMapper;






    @Override
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(order -> {
                    String clientName = fetchClientName(order.getClientId());
                    List<OrderItemDTO> orderItemDTOS = order.getItems().stream()
                            .map(OrderItemMapper::toOrderItemDTO).collect(Collectors.toList());
                    return orderMapper.toOrderDTO(order, clientName,orderItemDTOS);
                }).collect(Collectors.toList());
    }
    private String fetchClientName(Long clientId) {
        try {
            ClientDTO client = restTemplate.getForObject(
                    "http://CLIENTMANAGMENT/api/v1/clients/id/" + clientId, ClientDTO.class);
            return client.getFirstName() + " " + client.getLastName();
        } catch (Exception e) {
            return "Unknown";
        }
    }



    @Override
    public List<OrderDTO> getOrdersByClientId(Long clientId) {
        return orderRepository.findAllByClientId(clientId).stream().map(order -> {
            String clientName = fetchClientName(order.getClientId());
            List<OrderItemDTO> orderItemDTOS = order.getItems().stream()
                    .map(OrderItemMapper::toOrderItemDTO).collect(Collectors.toList());
            return orderMapper.toOrderDTO(order, clientName,orderItemDTOS);
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
