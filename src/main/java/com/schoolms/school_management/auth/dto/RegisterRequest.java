package com.schoolms.school_management.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * RegisterRequest
 */
public record RegisterRequest(

    @NotBlank(message = "Username is required") String username,

    @NotBlank @Size(min = 8, message = "Password must be at leaste 8 characters long") String password) {
}
