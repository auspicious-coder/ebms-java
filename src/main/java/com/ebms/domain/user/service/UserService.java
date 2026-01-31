package com.ebms.domain.user.service;

import com.ebms.api.auth.dto.LoginRequest;
import com.ebms.api.auth.dto.LoginResponse;
import com.ebms.api.auth.dto.RegisterRequest;
import com.ebms.domain.user.entity.User;

import java.util.List;

public interface UserService {

    LoginResponse login(LoginRequest request);

    void logout(Long userId);

    Long getUserIdFromToken(String token);

    User register(RegisterRequest request);

    List<User> getAllUsers();

    User getUserById(Long id);

    User updateUser(Long id, User updatedUser);
}
