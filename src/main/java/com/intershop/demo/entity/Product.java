package com.intershop.demo.entity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue
    private long id;

    @JsonProperty
    @Column(name = "name", nullable = false)
    private String name;

    @JsonProperty
    @Column(name = "price", nullable = false)
    private Double price;

}