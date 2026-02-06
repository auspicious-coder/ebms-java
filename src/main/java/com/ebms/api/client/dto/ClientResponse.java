package com.ebms.api.client.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ClientResponse {
    private Long id;
    private String clientName;
    private String gstNumber;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String status;
}
