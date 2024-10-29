package edu.famu.ecommerce.model.service;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.FirestoreOptions;
import edu.famu.ecommerce.model.Orders;
import edu.famu.ecommerce.model.RestOrders;
import edu.famu.ecommerce.model.Users;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminService {

    // A map to hold users' orders
    private Map<String, List<Orders>> userOrders = new HashMap<>(); // Maps userId to a list of Orders
    // A list to hold all users
    private List<Users> allUsers = new ArrayList<>(); // List of all users in the system

    // Method to retrieve all orders in the system (admin only)
    public List<Orders> getAllOrders() {
        List<Orders> allOrders = new ArrayList<>();
        for (List<Orders> orders : userOrders.values()) {
            allOrders.addAll(orders); // Collect all orders from all users
        }
        return allOrders;
    }

    // Method to retrieve orders with a specific status
    public List<Orders> getOrderByStatus(String status) {
        List<Orders> ordersByStatus = new ArrayList<>();
        for (List<Orders> orders : userOrders.values()) {
            for (Orders order : orders) {
                if (order.getOrderStatus() != null && order.getOrderStatus().equals(status)) {
                    ordersByStatus.add(order); // Collect orders matching the status
                }
            }
        }
        return ordersByStatus;
    }

    public RestOrders updateOrderStatus(String orderId, String status) {
        for (List<Orders> orders : userOrders.values()) {
            for (Orders order : orders) {
                if (order.getOrderId() != null && order.getOrderId().equals(orderId)) {
                    order.setOrderStatus(status); // Update the order's status

                    // Create a DocumentReference from orderId if necessary
                    DocumentReference docRef = getDocumentReferenceFromOrderId(order.getOrderId());

                    return new RestOrders(order.getCreatedAt(),
                            "Order status updated successfully",
                            order.getShippingInfo(),
                            order.getTotalAmount(),
                            docRef, // Use DocumentReference here
                            order.getProducts());
                }
            }
        }
        return new RestOrders(null, "Order not found", null, null, null, null); // Return if order not found
    }

    private DocumentReference getDocumentReferenceFromOrderId(String orderId) {
        return FirestoreOptions.getDefaultInstance().getService().collection("orders").document(orderId);
    }


    // Method to retrieve a list of all users (admin only)
    public List<Users> getAllUsers() {
        return new ArrayList<>(allUsers); // Return a copy of the user list
    }

    // Method to add a user to the system (for demonstration purposes)
    public void addUser(Users user) {
        allUsers.add(user); // Add a user to the list
    }

   

}

