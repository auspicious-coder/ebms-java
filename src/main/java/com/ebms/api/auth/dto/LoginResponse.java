package com.ebms.api.auth.dto;

import com.ebms.domain.user.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private Long userId;
    private String email;
    private Role role;
    private String token;
}
