package edu3431.matiukhin.productmanagment.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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


    private Double totalPrice;



    @Enumerated(EnumType.STRING)
    private Status status;

    private String deliveryAddress;

    private Long clientId;
    private Long productId;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();







    public OrderModel(Status status,  String deliveryAddress, Long clientId, Long productId) {
        this.clientId = clientId;
        this.productId = productId;

        this.status = status;
        this.deliveryAddress = deliveryAddress;
    }
}
