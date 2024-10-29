package edu.famu.ecommerce.model.util;

public class ProductResponse {

    private String productId;
    private String message;

    // Constructor
    public ProductResponse(String productId, String message) {
        this.productId = productId;
        this.message = message;
    }

    // Getters and Setters
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // toString for better readability (optional)
    @Override
    public String toString() {
        return "ProductResponse{" +
                "productId='" + productId + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
