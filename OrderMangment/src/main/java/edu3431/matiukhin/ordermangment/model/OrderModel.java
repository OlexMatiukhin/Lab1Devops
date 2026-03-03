package edu3431.matiukhin.ordermangment.model;

import dto.Status;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
@Getter
@Setter
public class OrderModel {



    @Id
    @GeneratedValue
    private Long id;

    private Double totalPrice;



    @Enumerated(EnumType.STRING)
    private Status status;

    private String deliveryAddress;

    private Long clientId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<OrderItem> items;





    public OrderModel(Status status,  String deliveryAddress, Long clientId, Long productId) {

        this.clientId = clientId;

        this.status = status;
        this.deliveryAddress = deliveryAddress;

    }
}
