package edu3431.matiukhin.clientmanagment.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;

@Data
@Table(name = "clients")
@Entity
@NoArgsConstructor
public class ClientModel {

    @Id
    @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String phone;
    private LocalDate birthDate;

    public ClientModel(String firstName, String lastName, String email, String phone, LocalDate birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.birthDate = birthDate;

    }

    public ClientModel(Long id,String firstName, String lastName, String email, String phone, LocalDate birthDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.birthDate = birthDate;

    }

    @Column(nullable = true)
    private int age;

    @PrePersist
    @PreUpdate
    public void calculateAge() {
        age=Period.between(birthDate, LocalDate.now()).getYears();
    }



}


