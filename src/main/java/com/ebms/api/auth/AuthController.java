package com.ebms.api.auth;

import com.ebms.api.auth.dto.LoginRequest;
import com.ebms.api.auth.dto.LoginResponse;
import com.ebms.api.auth.dto.RegisterRequest;
import com.ebms.common.CurrentUserProvider;
import com.ebms.common.util.ResponseUtils;
import com.ebms.common.response.ApiResponse;
import com.ebms.domain.user.entity.User;
import com.ebms.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final CurrentUserProvider currentUserProvider;

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseUtils.success(userService.login(request), "Login successful");
    }

    @PostMapping("/logout")
    public ApiResponse<String> logout(@RequestHeader("Authorization") String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            Long userId = userService.getUserIdFromToken(token);
            userService.logout(userId);
        }
        return ResponseUtils.success(null, "Logged out successfully");
    }

    @PostMapping("/register")
    public ApiResponse<User> register(@RequestBody RegisterRequest request) {
        return ResponseUtils.success(userService.register(request), "User registered successfully");
    }

    @GetMapping("/users")
    public ApiResponse<List<User>> getAllUsers() {
        return ResponseUtils.success(userService.getAllUsers(), "Users retrieved successfully");
    }

    @GetMapping("/users/{id}")
    public ApiResponse<User> getUserById(@PathVariable Long id) {
        return ResponseUtils.success(userService.getUserById(id), "User retrieved successfully");
    }

    @PutMapping("/users/{id}")
    public ApiResponse<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        return ResponseUtils.success(userService.updateUser(id, user), "User updated successfully");
    }

    @GetMapping("/currentUser")
    public ApiResponse<User> getCurrentUser() {
        return ResponseUtils.success(currentUserProvider.getCurrentUser(), "Current User Fatch successfully");
    }
}
