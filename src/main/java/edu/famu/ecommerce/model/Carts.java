package edu.famu.ecommerce.model;

import com.google.cloud.firestore.annotation.DocumentId;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class Carts {

    @DocumentId
    private Users userId;
    private ArrayList<Products> products;

    public Carts(String userId) {
        // Assuming Users class has a constructor that accepts a string
        this.userId = new Users(userId);
        this.products = new ArrayList<>(); // Initialize products list
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    // Constructor
    public Carts(String cartId, Users userId, ArrayList<Products> products) {
        this.userId = userId;
        this.products = products; // Assign products to the cart
    }

    // Method to add or update a product in the cart
    public void addOrUpdateProduct(String productId, int quantity) {
        if (quantity <= 0) {
            removeProduct(productId); // Remove the product if quantity is zero or negative
            return;
        }

        // Add or update logic (implementation not provided in your original code)
    }

    // Method to remove a product from the cart
    public void removeProduct(String productId) {
        if (products != null) {
            for (int i = 0; i < products.size(); i++) {
                Products product = products.get(i);
                if (product != null && productId.equals(product.getProductId())) {
                    products.remove(i); // Remove product by index
                    System.out.println("Product removed: " + productId);
                    return;
                }
            }
        }
        System.out.println("Product not in cart: " + productId); // If product not found
    }

    // Method to calculate the total cost of the cart
    public double calculateTotal() {
        double totalAmount = 0.0;
        for (Products product : products) { // Iterate through products
            if (product != null) {
                // Assuming Products class has a method getPrice()
                totalAmount += product.getPrice(); // Adjust based on your implementation
            }
        }
        return totalAmount;
    }
}
