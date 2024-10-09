package edu.famu.ecommerce.model;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.firebase.database.annotations.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Users {
    @DocumentId
    private @Nullable String userId;
    private String email;
    private String name;
    private String role;
    private String phoneNumber;
    private Map<String,Object> address;


}
