package edu.famu.ecommerce.model.controller;


import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import edu.famu.ecommerce.model.Users;
import edu.famu.ecommerce.model.service.UserService;
import edu.famu.ecommerce.model.util.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private UserService service;

    public UserController(UserService service) {
        this.service = service;
    }
    @PostMapping("/")
    public ResponseEntity<ApiResponse<String>> createUser(@RequestBody HashMap<String, Object> user){
        try{
            Users users = null;
            users.setUserId((String)user.get("userId"));
            users.setEmail((String)user.get("email"));
            users.setName((String)user.get("displayName"));


            ArrayList<String> list = (ArrayList<String>) user.get("tasks");

            ArrayList<DocumentReference> tasksRef = new ArrayList<>();

            if(!list.isEmpty()){

                Firestore db = FirestoreClient.getFirestore();
                for(String task : list) {

                    tasksRef.add(db.collection("Tasks").document(task));
                }

            }

            String id = service.createUser(users);

            return ResponseEntity.status(201).body(new ApiResponse<>(true, "User created", id, null ));

        } catch (ExecutionException e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "Interval Server error", null, e));

        } catch (InterruptedException e) {
            return ResponseEntity.status(503).body(new ApiResponse<>(false, "Unable to reach firebase", null, e));
        }
    }
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<Users>> getUserById(@PathVariable(name="userId") String userId) {
        try {
            Users user = service.getUserById(userId);
            if (user != null)
                return ResponseEntity.ok(new ApiResponse<>(true, "User found", user, null));
            else
                return ResponseEntity.status(404).body(new ApiResponse<>(true, "User not found", null, null));


        } catch (ParseException | ExecutionException e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "Internal Server error", null, e));
        } catch (InterruptedException e) {
            return ResponseEntity.status(503).body(new ApiResponse<>(false, "Unable to reach firebase", null, e));
        }
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<Users>>> getAllUsers() {
        try {
            List<Users> users = service.getAllUsers();
            if(users != null)
                return ResponseEntity.ok(new ApiResponse<>(true, "Users List", users, null));
            else
                return ResponseEntity.status(204).body(new ApiResponse<>(true, "No users", null, null));

        } catch (ExecutionException e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "Internal Server error", null, e));
        } catch (InterruptedException e) {
            return ResponseEntity.status(503).body(new ApiResponse<>(false, "Unable to reach firebase", null, e));

        }
    }
}

