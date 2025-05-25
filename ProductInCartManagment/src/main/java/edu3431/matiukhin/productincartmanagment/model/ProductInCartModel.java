package edu3431.matiukhin.productincartmanagment.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@ToString
@Table(name = "products_in_cart")
public class ProductInCartModel {

    @Id
    @GeneratedValue
    private Long id;

    private String productName;
    private int count;
    private Double totalPrice;
    private Long clientId;
    private Long productId;

    public ProductInCartModel(String productName, int count, Double totalPrice, Long clientId, Long productId) {
        this.productName = productName;
        this.count = count;
        this.totalPrice = totalPrice;
        this.clientId = clientId;
        this.productId = productId;
    }

    public ProductInCartModel() {

    }
}


