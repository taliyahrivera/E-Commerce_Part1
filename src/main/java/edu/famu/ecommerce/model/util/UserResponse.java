package edu.famu.ecommerce.model.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponse {

    private String userId;        // The unique ID of the user
    private String message;       // A confirmation message (e.g., "Registration successful")

}

