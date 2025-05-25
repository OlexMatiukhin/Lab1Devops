package edu3431.matiukhin.ordermangment.mapper;/*
@author sasha
@project springshop
@class OrderItemMapper
@version 1.0.0
@since 22.03.2025 - 20 - 41
*/



import dto.OrderItemDTO;
import edu3431.matiukhin.ordermangment.model.OrderItem;

public class OrderItemMapper {
    public static OrderItemDTO toOrderItemDTO(OrderItem orderItem){
        return  new OrderItemDTO(orderItem.getId(), orderItem.getProductName(), orderItem.getCount(), orderItem.getTotalPrice());

    }
}


