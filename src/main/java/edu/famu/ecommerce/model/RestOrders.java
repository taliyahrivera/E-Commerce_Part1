package edu.famu.ecommerce.model;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestOrders extends AOrders{
    private DocumentReference orderId;
    private ArrayList<Map<String,Object>> products;

    public RestOrders(Timestamp createdAt, String orderStatus, ArrayList<Map<String, Object>> shippingInfo, Integer totalAmount, DocumentReference orderId, ArrayList<Map<String, Object>> products) {
        super(createdAt, orderStatus, shippingInfo, totalAmount);
        this.orderId = orderId;
        this.products = products;
    }
}
