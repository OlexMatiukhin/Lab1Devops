package edu3431.matiukhin.clientmanagment.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderModel {

    public enum Status {
        ACTIVE,
        FINISHED,
        RETURNED,
        CANCELLED
    }

    @Id
    @GeneratedValue
    private Long id;
    private String productName;
    private Double totalPrice;
    @Enumerated(EnumType.STRING)
    private Status status;
    private String deliveryAddress;
    private Long clientId;
    private Long productId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<OrderItem> items;
    public OrderModel(String productName,  Status status,  String deliveryAddress, Long clientId, Long productId) {
        this.productName=productName;
        this.clientId = clientId;
        this.productId = productId;
        this.status = status;
        this.deliveryAddress = deliveryAddress;
    }
}
