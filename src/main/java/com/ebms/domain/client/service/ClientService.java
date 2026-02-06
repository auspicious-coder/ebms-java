package com.ebms.domain.client.service;

import java.util.List;

import com.ebms.api.client.dto.ClientRequest;
import com.ebms.api.client.dto.ClientResponse;
import com.ebms.common.response.ApiResponse;
import com.ebms.domain.client.entity.Client;

public interface ClientService {
    ApiResponse<ClientResponse> addClient(ClientRequest clientRequest);
    ApiResponse<ClientResponse> updateClient(Long clientId, ClientRequest clientRequest);
    ApiResponse<ClientResponse> updateClientStatus(Long clientId, String status);
    ApiResponse<ClientResponse> getByClientId(Long clientId);
    ApiResponse<List<ClientResponse>> getAllClient(int flag);
}
