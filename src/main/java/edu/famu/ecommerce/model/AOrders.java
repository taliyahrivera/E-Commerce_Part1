package edu.famu.ecommerce.model;

import com.google.cloud.Timestamp;
import com.google.protobuf.util.Timestamps;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Map;

@Data
@NoArgsConstructor

public abstract class AOrders {
    private Timestamp createdAt;
    private String orderStatus;
    private ArrayList<Map<String,Object>> shippingInfo;
    private Integer totalAmount;

    private void setTimestampAt(String createdAt) throws ParseException {
        this.createdAt = Timestamp.fromProto(Timestamps.parse(createdAt));
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public AOrders(Timestamp createdAt, String orderStatus, ArrayList<Map<String, Object>> shippingInfo, Integer totalAmount) {
        this.createdAt = createdAt;
        this.orderStatus = orderStatus;
        this.shippingInfo = shippingInfo;
        this.totalAmount = totalAmount;
    }
}
