package com.schoolms.school_management.user.DTO;

import com.schoolms.school_management.user.Role;

/**
 * UserResponse
 */
public record UserResponse(
    Long id,
    String firstName,
    String lastName,
    String email,
    Role role) {
}
