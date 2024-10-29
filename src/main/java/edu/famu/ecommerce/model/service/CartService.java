package edu.famu.ecommerce.model.service;


import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import edu.famu.ecommerce.model.Carts;
import edu.famu.ecommerce.model.util.CartResponse;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class CartService {

    private Firestore firestore;
    private static final String CARTS_COLLECTION = "Carts";

    public CartService() {
        this.firestore = FirestoreClient.getFirestore();
    }

    // Retrieves the shopping cart for a specific user by userId
    public Carts getCartByUserId(String userId) throws ExecutionException, InterruptedException {
        DocumentReference cartRef = firestore.collection(CARTS_COLLECTION).document(userId);
        DocumentSnapshot cartSnap = cartRef.get().get();
        return cartSnap.exists() ? cartSnap.toObject(Carts.class) : new Carts(userId); // Return new cart if none exists
    }

    // Adds a product to the user's cart or updates the quantity if the product already exists in the cart
    public CartResponse addProductToCart(String userId, String productId, int quantity) throws ExecutionException, InterruptedException {
        DocumentReference cartRef = firestore.collection(CARTS_COLLECTION).document(userId);
        Carts cart = getCartByUserId(userId);

        cart.addOrUpdateProduct(productId, quantity); // Adds product or updates quantity
        cartRef.set(cart).get(); // Save updated cart to Firestore

        return new CartResponse(userId, "Product added or updated successfully.");
    }

    // Removes a product from the user's cart
    public CartResponse removeProductFromCart(String userId, String productId) throws ExecutionException, InterruptedException {
        DocumentReference cartRef = firestore.collection(CARTS_COLLECTION).document(userId);
        Carts cart = getCartByUserId(userId);

        cart.removeProduct(productId); // Remove product from cart
        cartRef.set(cart).get(); // Save updated cart

        return new CartResponse(userId, "Product removed successfully.");
    }

    // Calculates the total price of the user's cart by summing up each product's price * quantity
    public double calculateCartTotal(String userId) throws ExecutionException, InterruptedException {
        Carts cart = getCartByUserId(userId);
        return cart.calculateTotal(); // Uses cart's method to calculate total
    }

    // Clears all items from the user's cart
    public CartResponse clearCart(String userId) throws ExecutionException, InterruptedException {
        DocumentReference cartRef = firestore.collection(CARTS_COLLECTION).document(userId);
        Carts cart = new Carts(userId); // Empty cart for user
        cartRef.set(cart).get(); // Save the empty cart

        return new CartResponse(userId, "Cart cleared successfully.");
    }
}

