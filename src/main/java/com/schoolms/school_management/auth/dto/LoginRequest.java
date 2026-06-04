package com.schoolms.school_management.auth.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * LoginRequest
 */
public record LoginRequest(
    @NotBlank(message = "username is required") String username,

    @NotBlank(message = "password is required") String password) {

}
