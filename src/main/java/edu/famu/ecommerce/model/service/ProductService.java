package edu.famu.ecommerce.model.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import edu.famu.ecommerce.model.util.ProductResponse;
import edu.famu.ecommerce.model.Products;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;



@Service
public class ProductService {

    private Firestore firestore;
    private static final String PRODUCTS_COLLECTION = "Products";

    public ProductService() {
        this.firestore = FirestoreClient.getFirestore();
    }

    //Retrieve all products
    public List<Products> getAllProducts() throws ExecutionException, InterruptedException {
        CollectionReference productsCollection = firestore.collection(PRODUCTS_COLLECTION);
        ApiFuture<QuerySnapshot> querySnapshot = productsCollection.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();

        List<Products> products = documents.isEmpty() ? null : new ArrayList<>();

        documents.forEach(document -> {
            Products product = document.toObject(Products.class);
            product.setProductId(document.getId());
            products.add(product);
        });

        return products;
    }

    // Retrieve a product by its productId
    public Products getProductById(String productId) throws ExecutionException, InterruptedException {
        DocumentReference productRef = firestore.collection(PRODUCTS_COLLECTION).document(productId);
        DocumentSnapshot productSnap = productRef.get().get();

        if (productSnap.exists()) {
            Products product = productSnap.toObject(Products.class);
            product.setProductId(productSnap.getId());
            return product;
        }
        return null;
    }

    // Add a new product
    public ProductResponse addProduct(Products product) throws ExecutionException, InterruptedException {
        ApiFuture<DocumentReference> writeResult = firestore.collection(PRODUCTS_COLLECTION).add(product);
        DocumentReference documentRef = writeResult.get();
        return new ProductResponse(documentRef.getId(), "Product added successfully.");
    }

    // Update an existing product
    public ProductResponse updateProduct(String productId, Products product) throws ExecutionException, InterruptedException {
        DocumentReference productRef = firestore.collection(PRODUCTS_COLLECTION).document(productId);
        ApiFuture<WriteResult> writeResult = productRef.set(product);
        writeResult.get();
        return new ProductResponse(productId, "Product updated successfully.");
    }

    // Delete a product from the database
    public ProductResponse deleteProduct(String productId) throws ExecutionException, InterruptedException {
        DocumentReference productRef = firestore.collection(PRODUCTS_COLLECTION).document(productId);
        ApiFuture<WriteResult> writeResult = productRef.delete();
        writeResult.get();
        return new ProductResponse(productId, "Product deleted successfully.");
    }
}

