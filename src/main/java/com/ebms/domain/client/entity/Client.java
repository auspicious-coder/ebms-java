package com.ebms.domain.client.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long clientId;

    @Column(name = "name")
    private String clientName;

    @Column(name = "gst_number", length = 50)
    private String gstNumber;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "status", length = 50)
    private String status;
}
