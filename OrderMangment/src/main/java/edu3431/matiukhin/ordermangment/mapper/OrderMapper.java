package edu3431.matiukhin.ordermangment.mapper;



import dto.*;
import edu3431.matiukhin.ordermangment.model.OrderItem;
import edu3431.matiukhin.ordermangment.model.OrderModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor

public class OrderMapper {
    private final RestTemplate restTemplate;

    public OrderDTO toOrderDTO(OrderModel order) {
        String urlProductById = "http://CLIENTMANAGMENT/api/v1/clients/id/" + order.getClientId();
        ClientDTO client = restTemplate.getForObject(urlProductById, ClientDTO.class);
        String clientFullName = client.getFirstName() + client.getLastName();
        List<OrderItemDTO> orderItemDTOS = order.getItems().stream().map(OrderItemMapper::toOrderItemDTO).collect(Collectors.toList());
        return new OrderDTO(order.getId(), order.getStatus(), clientFullName, orderItemDTOS);
    }


    public OrderModel toOrder(SaveOrderDTO order) {
        List<SaveOrderItemDTO> saveOrderItems = order.getItems();
        List<OrderItem> orderItems = new ArrayList<>();
        OrderModel orderModel = new OrderModel();
        orderModel.setTotalPrice(order.getTotalPrice());
        orderModel.setStatus(Status.ACTIVE);
        orderModel.setDeliveryAddress(order.getDeliveryAddress());
        orderModel.setClientId(order.getClientId());

        for (SaveOrderItemDTO saveOrderItem : saveOrderItems) {
            OrderItem orderItem = new OrderItem(saveOrderItem.getProductName(), saveOrderItem.getCount(), saveOrderItem.getTotalPrice(), saveOrderItem.getProductId());
            orderItem.setOrder(orderModel);
            orderItems.add(orderItem);
        }
        orderModel.setItems(orderItems);
        return orderModel;


    }


}







