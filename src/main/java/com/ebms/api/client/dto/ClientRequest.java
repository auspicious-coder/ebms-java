package com.ebms.api.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
public class ClientRequest {
    @JsonProperty("clientName")
    private String clientName;

    @JsonProperty("gstNumber")
    private String gstNumber;
}


