package edu.famu.ecommerce.model.util;

public record ApiResponse<T>(boolean success, String message, T data, Object error) {
}


