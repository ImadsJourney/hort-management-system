package com.schoolms.school_management.auth.dto;

/**
 * AuthResponse
 */
public record AuthResponse(
    Long userId,
    String username,
    String token,
    String message) {
}
