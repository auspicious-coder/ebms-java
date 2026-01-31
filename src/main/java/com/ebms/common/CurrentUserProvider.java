package com.ebms.common;

import com.ebms.common.security.JwtTokenProvider;
import com.ebms.domain.user.entity.User;
import com.ebms.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;

@Component
@RequiredArgsConstructor
public class CurrentUserProvider {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final HttpServletRequest request;


    public User getCurrentUser() {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            return null;
        }
        String token = header.substring(7);
        if (!jwtTokenProvider.validateToken(token)) {
            return null;
        }
        Long userId = jwtTokenProvider.getUserId(token);
        return userRepository.findById(userId).orElse(null);
    }
}
