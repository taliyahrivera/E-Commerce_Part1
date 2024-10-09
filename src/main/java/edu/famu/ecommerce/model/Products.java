package edu.famu.ecommerce.model;

import com.google.cloud.Timestamp;
import com.google.firebase.database.annotations.Nullable;
import com.google.protobuf.util.Timestamps;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.text.ParseException;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Products {
    private @Nullable String productId;
    private String category;
    private String description;
    private String imageUrl;
    private String name;
    private Timestamp createdAt;

    private void setTimestampAt(String createdAt) throws ParseException {
        this.createdAt = Timestamp.fromProto(Timestamps.parse(createdAt));
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
