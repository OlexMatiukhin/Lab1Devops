package edu3431.matiukhin.ordermangment.controller;


import dto.ChangeOrderStatusDTO;
import dto.OrderDTO;
import dto.SaveOrderDTO;

import edu3431.matiukhin.ordermangment.service.OrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;
    @GetMapping
    public List<OrderDTO> getAllOrders() {
        return orderService.getAllOrders();
    }

    @PostMapping("/change_status")
    public void changeStatus(@Valid @RequestBody ChangeOrderStatusDTO changeOrderStatusDTO) {
       orderService.changeStatus(changeOrderStatusDTO.getOrderId(), changeOrderStatusDTO.getOrderStatus());
    }




    @PostMapping("/save_order")
    public void changeStatus( @RequestBody SaveOrderDTO order) {
        orderService.saveNewOrder(order);
    }


    @GetMapping("{clientId}")
    public List<OrderDTO> getProductsByClientId(@PathVariable Long clientId) {
        return orderService.getOrdersByClientId(clientId);
    }

    @DeleteMapping("{orderId}")
    public void deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
    }
}
