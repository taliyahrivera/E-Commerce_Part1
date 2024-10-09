package edu.famu.ecommerce.model;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.annotation.DocumentId;
import com.google.firebase.database.annotations.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Map;

@Data
@NoArgsConstructor
public class Orders extends AOrders{
    @DocumentId
    private @Nullable String orderId;
    private ArrayList<Map<String,Object>> products;
    private Users userId;

    public Orders(String orderId, ArrayList<Map<String, Object>> products, Users userId) {
        this.orderId = orderId;
        this.products = products;
        this.userId = userId;
    }

}
