package edu.famu.ecommerce.model;


import com.google.cloud.firestore.annotation.DocumentId;
import com.google.firebase.database.annotations.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public abstract class ACarts {
    @DocumentId
    private @Nullable String cartsId;
    private double totalAmount;

    public ACarts(String cartsId, double totalAmount) {
        this.cartsId = cartsId;
        this.totalAmount = totalAmount;
    }
}
