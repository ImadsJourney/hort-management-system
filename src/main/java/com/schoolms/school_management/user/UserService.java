package com.schoolms.school_management.user;

import org.springframework.stereotype.Service;

import com.schoolms.school_management.user.DTO.CreateUserRequest;
import com.schoolms.school_management.user.DTO.UserResponse;

import lombok.RequiredArgsConstructor;

/**
 * UserService
 */
@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;

  public UserResponse createUser(CreateUserRequest request) {
    User user = User.builder()
        .firstName(request.firstName())
        .lastName(request.lastName())
        .email(request.email())
        .password(request.password())
        .role(request.role())
        .build();

    User savedUser = userRepository.save(user);

    return new UserResponse(
        savedUser.getId(),
        savedUser.getFirstName(),
        savedUser.getLastName(),
        savedUser.getEmail(),
        savedUser.getRole());
  }

}
