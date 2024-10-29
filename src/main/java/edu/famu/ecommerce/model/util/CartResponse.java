package edu.famu.ecommerce.model.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartResponse {
    private String cartId; // ID of the cart for which the response applies
    private String message; // A message indicating the result of the operation (e.g., "Product added successfully")

    // Optional: Include the total amount for convenience
    private double totalAmount;

    public CartResponse(String cartId, String message) {
        this.cartId = cartId;
        this.message = message;
    }
}

