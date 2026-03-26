package edu3431.matiukhin.ordermangment.service;


import dto.OrderDTO;
import dto.SaveOrderDTO;
import dto.Status;

import java.util.List;
import java.util.Map;

public interface OrderService {

    public void deleteOrder (Long order_id);
    public void changeStatus(Long order_id, Status status);
    public List<OrderDTO> getAllOrders();
   //public void saveOrder(SaveOrderDTO order);
    List<OrderDTO> getOrdersByClientId(Long clientId);
    void saveNewOrder(SaveOrderDTO order);
     Map<Long, List<OrderDTO>> getOrdersByClientIds(List<Long> clientIds);
}
