package com.ebms.domain.client.service.Impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ebms.api.client.dto.ClientRequest;
import com.ebms.api.client.dto.ClientResponse;
import com.ebms.common.response.ApiResponse;
import com.ebms.common.util.ResponseUtils;
import com.ebms.domain.client.entity.Client;
import com.ebms.domain.client.repository.ClientRepository;
import com.ebms.domain.client.service.ClientService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ClientServiceImpl implements ClientService{

    @Autowired
    private ClientRepository clientRepo;

    @Override
    public ApiResponse<ClientResponse> addClient(ClientRequest clientRequest) {
        try{
            Client client = new Client();
            client.setClientName(clientRequest.getClientName());
            client.setGstNumber(clientRequest.getGstNumber());
            client.setCreatedAt(LocalDateTime.now());
            client.setStatus("y");
            return ResponseUtils.success(mapClientResponse(clientRepo.save(client)), "Client added successfully");
        } catch (Exception e){
            return ResponseUtils.failure("Failed to add client", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ApiResponse<ClientResponse> updateClient(Long clientId, ClientRequest clientRequest) {
        try {
            Optional<Client> clientOptional = clientRepo.findById(clientId);
            if(clientOptional.isPresent()){
                Client client = new Client();
                client.setClientName(clientRequest.getClientName());
                client.setGstNumber(clientRequest.getGstNumber());
                client.setUpdatedAt(LocalDateTime.now());
                return ResponseUtils.success(mapClientResponse(clientRepo.save(client)), "Client updated successfully");
            } else {
                return ResponseUtils.failure("client_id not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return ResponseUtils.failure("Failed to update client", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ApiResponse<ClientResponse> updateClientStatus(Long clientId, String status) {
        try {
            Optional<Client> clientOptional = clientRepo.findById(clientId);
            if(clientOptional.isPresent()){
                Client client = new Client();
                if(status.equalsIgnoreCase("y") || status.equalsIgnoreCase("n")){
                    client.setStatus(status); 
                } else{
                    return ResponseUtils.failure("Use either y or n", HttpStatus.BAD_REQUEST);
                }
                client.setUpdatedAt(LocalDateTime.now());
                return ResponseUtils.success(mapClientResponse(clientRepo.save(client)), "Client status updated successfully");
            } else {
                return ResponseUtils.failure("client_id not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return ResponseUtils.failure("Failed to update client status", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ApiResponse<ClientResponse> getByClientId(Long clientId) {
        try {
            Optional<Client> client = clientRepo.findById(clientId);
            if(client.isPresent()){
                Client clientObj = client.get();
                return ResponseUtils.success(mapClientResponse(clientObj), "success");
            } else {
                return ResponseUtils.failure("Data with client_id not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return ResponseUtils.failure("Failed to retrieve client", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ApiResponse<List<ClientResponse>> getAllClient(int flag) {
        try {
            List<Client> client;
            if(flag == 1){
                client = clientRepo.findByStatusOrderByClientNameAsc("y");
            } else if(flag == 0){
                client = clientRepo.findAllByOrderByStatusDescUpdatedAtDesc();
            } else{
                return ResponseUtils.failure("Use either 0 and 1", HttpStatus.BAD_REQUEST);
            }
            List<ClientResponse> clients = client.stream()
                    .map(this::mapClientResponse)
                    .collect(Collectors.toList());
            return ResponseUtils.success(clients, "success");
        } catch (Exception e) {
            return ResponseUtils.failure("Failed to retrieve clients", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ClientResponse mapClientResponse(Client client){
        ClientResponse clientResponse = new ClientResponse();
        clientResponse.setId(client.getClientId());
        clientResponse.setClientName(client.getClientName());
        clientResponse.setGstNumber(client.getGstNumber());
        clientResponse.setCreatedAt(client.getCreatedAt());
        clientResponse.setUpdatedAt(client.getUpdatedAt());
        clientResponse.setStatus(client.getStatus());
        return clientResponse;
    }

}

