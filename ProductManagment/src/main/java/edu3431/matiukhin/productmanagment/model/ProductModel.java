package edu3431.matiukhin.productmanagment.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "products")
public class ProductModel {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String category;
    private String type;
    private Double price;
    private int count;
    @Column(nullable = true)
    private String status;

    public ProductModel(String name, String category,String type, Double price, int count){
        this.name = name;
        this.category = category;
        this.type = type;
        this.price = price;
        this.count = count;
    }

    public ProductModel() {

    }


    @PrePersist
    @PreUpdate
    public void calculateAge() {
        if(count>0){
            status="В наявності";
        }
        else{
            status="Немає в наявності";
        }

    }
    public void ensureNumberIsPositive() {
        if (this.count < 0) {
            this.count = 0;
        }
    }


}

