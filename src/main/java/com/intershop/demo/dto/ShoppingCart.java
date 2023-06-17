package com.intershop.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.intershop.demo.entity.ShoppingCartItem;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCart implements Serializable {
    @JsonProperty("cartItems")
    private List<ShoppingCartItem> cartItems;

    @JsonProperty("totalPrice")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_FLOAT, pattern = "#0.00")
    private double totalPrice;

}
