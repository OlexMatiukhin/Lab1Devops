package edu3431.matiukhin.clientmanagment.model;/*
@author sasha
@project springshop
@class OrderItem
@version 1.0.0
@since 22.03.2025 - 18 - 01
*/

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue
    private Long id;
    private String productName;
    private int count;
    private Double totalPrice;
    private Long productId;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderModel order;

    public OrderItem(String productName, int count, Double totalPrice,  Long productId) {
        this.productName = productName;
        this.count = count;
        this.totalPrice = totalPrice;


        this.productId = productId;

    }

}
