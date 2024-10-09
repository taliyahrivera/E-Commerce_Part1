package edu.famu.ecommerce.model;

import com.google.cloud.firestore.DocumentReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Map;

@Data
@NoArgsConstructor
public class RestCarts extends ACarts{
    private DocumentReference userId;
    private ArrayList<Map<String,Object>> products;

    public RestCarts(String cartsId, double totalAmount, DocumentReference userId, ArrayList<Map<String, Object>> products) {
        super(cartsId, totalAmount);
        this.userId = userId;
        this.products = products;
    }
}
