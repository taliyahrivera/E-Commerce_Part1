package edu.famu.ecommerce.model.service;

import edu.famu.ecommerce.model.Orders;
import edu.famu.ecommerce.model.RestOrders;
import edu.famu.ecommerce.model.Users;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderService {

    // A map to hold users' orders
    private Map<String, List<Orders>> userOrders = new HashMap<>(); // Maps userId to a list of Orders

    // Method to place an order using the user's cart and shipping information
    public RestOrders placeOrder(String userId, Orders orderRequest) {
        // Retrieve user's current orders, or create a new list if none exist
        List<Orders> orders = userOrders.getOrDefault(userId, new ArrayList<>());

        // Add the new order to the user's order list
        orders.add(orderRequest);

        // new order list
        userOrders.put(userId, orders);

        //confirmation response
        return new RestOrders(orderRequest.getCreatedAt(), "Order placed successfully", orderRequest.getShippingInfo(), orderRequest.getTotalAmount(), orderRequest.getOrderId(), orderRequest.getProducts());
    }

    // Method to retrieve all past orders for a user
    public List<Orders> getOrdersByUserId(String userId) {
        // Return the list of orders for the user, or an empty list if none exist
        return userOrders.getOrDefault(userId, new ArrayList<>());
    }

    // Method to retrieve details of a specific order by order ID
    public Orders getOrderById(String orderId) {
        // Search through all users' orders to find the specific order
        for (List<Orders> orders : userOrders.values()) {
            for (Orders order : orders) {
                if (order.getOrderId() != null && order.getOrderId().equals(orderId)) {
                    return order; // Return the order if found
                }
            }
        }
        return null; // Return null if the order is not found
    }

    // Method to cancel an order before it is shipped
    public RestOrders cancelOrder(String orderId) {
        for (List<Orders> orders : userOrders.values()) {
            for (int i = 0; i < orders.size(); i++) {
                Orders order = orders.get(i);
                if (order.getOrderId() != null && order.getOrderId().equals(orderId)) {
                    orders.remove(i); // Remove the order from the list
                    return new RestOrders(order.getCreatedAt(), "Order cancelled successfully", order.getShippingInfo(), order.getTotalAmount(), order.getOrderId(), order.getProducts());
                }
            }
        }
        return new RestOrders(null, "Order not found", null, null, null, null); // Return response if order not found
    }
}

