package edu.famu.ecommerce.model.service;


import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import edu.famu.ecommerce.model.Users;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class UserService {

    private Firestore firestore;
    private static final String USERS_COLLECTION = "Users";
    private static final String TASKS_COLLECTION = "Tasks";

    public UserService() {
        this.firestore = FirestoreClient.getFirestore();
    }

    private Users documentToUser(DocumentSnapshot document) throws ParseException {
        Users user = null;

        if (document.exists()) {
            user = new Users();
            user.setUserId(document.getId());
            user.setDisplayName(document.getString("displayName"));
            user.setEmail(document.getString("email"));
            user.setCreatedAt(document.getTimestamp("createdAt").toString());
            user.setTasks(null);
        }

        return user;
    }

    public List<Users> getAllUsers() throws ExecutionException, InterruptedException {
        CollectionReference usersCollection = firestore.collection(USERS_COLLECTION);
        ApiFuture<QuerySnapshot> querySnapshot = usersCollection.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();

        List<Users> users = documents.isEmpty() ? null : new ArrayList<>();

        documents.forEach(document -> {
            Users user;
            try {
                user = documentToUser(document);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            users.add(user);
        });
        return users;
    }

    public Users getUserById(String userId) throws ExecutionException, InterruptedException, ParseException {
        DocumentReference usersRef = firestore.collection(USERS_COLLECTION).document(userId);
        DocumentSnapshot userSnap = usersRef.get().get();
        return documentToUser(userSnap);
    }

    public List<Users> getUsersByEmail(String email) throws ExecutionException, InterruptedException {
        Query query = firestore.collection(USERS_COLLECTION).whereEqualTo("email", email);
        ApiFuture<QuerySnapshot> future = query.get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        List<Users> users = documents.isEmpty() ? null : new ArrayList<>();

        documents.forEach(document -> {
            Users user;
            if (document.exists()) {
                try {
                    user = documentToUser(document);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                users.add(user);
            }
        });
        return users;
    }

    public String createUser(Users user) throws ExecutionException, InterruptedException {
        ApiFuture<DocumentReference> writeResult = firestore.collection(USERS_COLLECTION).add(user);
        DocumentReference rs = writeResult.get();
        return rs.getId();
    }

    // New Method: Register a new user and return a UserResponse with confirmation of registration
    public UserResponse registerUser(Users user) throws ExecutionException, InterruptedException {
        ApiFuture<DocumentReference> writeResult = firestore.collection(USERS_COLLECTION).add(user);
        DocumentReference documentRef = writeResult.get();
        return new UserResponse(documentRef.getId(), "Registration successful.");
    }

    // New Method: Retrieve user details by userId
    public Users getUserDetails(String userId) throws ExecutionException, InterruptedException, ParseException {
        DocumentReference userRef = firestore.collection(USERS_COLLECTION).document(userId);
        DocumentSnapshot userSnap = userRef.get().get();
        return documentToUser(userSnap);
    }

    // New Method: Update user profile and return a UserResponse with confirmation of the update
    public UserResponse updateUserProfile(String userId, Users user) throws ExecutionException, InterruptedException {
        DocumentReference userRef = firestore.collection(USERS_COLLECTION).document(userId);
        ApiFuture<WriteResult> writeResult = userRef.set(user);
        writeResult.get();
        return new UserResponse(userId, "User profile updated successfully.");
    }
}
