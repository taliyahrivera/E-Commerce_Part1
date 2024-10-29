package edu.famu.ecommerce.model;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.annotation.DocumentId;
import com.google.firebase.database.annotations.Nullable;
import com.google.protobuf.util.Timestamps;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.ParseException;
import java.util.List;
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
    private String displayName;
    private Timestamp createdAt;
    private List<Object> tasks;


    public Users(String userId) {
        this.userId = userId;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setCreatedAt(String createdAt) {
        try {
            this.createdAt = Timestamp.fromProto(Timestamps.parse(createdAt));
        } catch (ParseException e) {
            System.out.println("Invalid timestamp format: " + createdAt);
        }
    }

    public void setTasks(Object o) {
        if (o instanceof List<?>) {
            this.tasks = (List<Object>) o;
        } else {
            System.out.println("Invalid input: tasks must be a list.");
        }
    }
}
