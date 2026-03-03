package edu3431.matiukhin.ordermangment.mapper;



import dto.*;
import edu3431.matiukhin.ordermangment.model.OrderItem;
import edu3431.matiukhin.ordermangment.model.OrderModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderMapper {

    public OrderDTO toOrderDTO(OrderModel order, String clientFullName, List<OrderItemDTO> orderItems) {
        return new OrderDTO(order.getId(), order.getStatus(), clientFullName, orderItems);
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
