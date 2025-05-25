package edu3431.matiukhin.ordermangment.service;



import dto.OrderDTO;
import dto.SaveOrderDTO;
import dto.Status;

import edu3431.matiukhin.ordermangment.exeption.ElementNotFoundInBaseExeption;
import edu3431.matiukhin.ordermangment.mapper.OrderMapper;
import edu3431.matiukhin.ordermangment.model.OrderModel;
import edu3431.matiukhin.ordermangment.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    OrderRepository orderRepository;

    private final OrderMapper orderMapper;



    @Override
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream().map(orderMapper::toOrderDTO).collect(Collectors.toList());
    }


    @Override
    public List<OrderDTO> getOrdersByClientId(Long clientId) {
        return orderRepository.findAllByClientId(clientId).stream().map(orderMapper::toOrderDTO).collect(Collectors.toList());
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
