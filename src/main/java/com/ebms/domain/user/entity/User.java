package com.ebms.domain.user.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }

    public void setLastLoginAt(LocalDateTime lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }

    @Column(nullable = false)
    private String password;

    @Column(name = "current_token_id")
    private String currentTokenId;

    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;



    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;




    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public String getCurrentTokenId() {
        return currentTokenId;
    }

    public void setCurrentTokenId(String currentTokenId) {
        this.currentTokenId = currentTokenId;
    }

}
