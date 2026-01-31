package com.ebms.domain.user.service.impl;

import com.ebms.api.auth.dto.LoginRequest;
import com.ebms.api.auth.dto.LoginResponse;
import com.ebms.api.auth.dto.RegisterRequest;
import com.ebms.common.security.JwtTokenProvider;
import com.ebms.domain.user.entity.User;
import com.ebms.domain.user.repository.UserRepository;
import com.ebms.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        user.setLastLoginAt(LocalDateTime.now());
        String token = jwtTokenProvider.generateAccessToken(user);

        return new LoginResponse(
                user.getId(),
                user.getEmail(),
                user.getRole(),
                token
        );
    }

    @Override
    public void logout(Long userId) {
        jwtTokenProvider.invalidateUserToken(userId);
    }

    @Override
    public Long getUserIdFromToken(String token) {
        return jwtTokenProvider.getUserId(token);
    }

    @Override
    public User register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    @Override
    public User updateUser(Long id, User updatedUser) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setRole(updatedUser.getRole());

        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        return userRepository.save(existingUser);
    }
}
