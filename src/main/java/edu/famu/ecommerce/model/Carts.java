package edu.famu.ecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Carts extends ACarts{

    private Users userId;
    private ArrayList<Map<String,Object>> products;

    public Carts(String cartsId, double totalAmount, Users userId, ArrayList<Map<String, Object>> products) {
        super(cartsId, totalAmount);
        this.userId = userId;
        this.products = products;
    }
}
