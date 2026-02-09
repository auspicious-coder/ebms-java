package com.ebms.api.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ebms.api.client.dto.ClientRequest;
import com.ebms.api.client.dto.ClientResponse;
import com.ebms.common.response.ApiResponse;
import com.ebms.domain.client.service.ClientService;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Client Controller")
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/create")
    public ResponseEntity<?> createClient(@RequestBody ClientRequest clientRequest){
        return new ResponseEntity<>(clientService.addClient(clientRequest), HttpStatus.CREATED);
    }

    @PutMapping("/update/{clientId}")
    public ResponseEntity<ApiResponse<ClientResponse>> updateClient(@PathVariable Long clientId, @RequestBody ClientRequest clientRequest){
        return new ResponseEntity<>(clientService.updateClient(clientId, clientRequest), HttpStatus.OK);
    }

    @PutMapping("/update-status/{clientId}")
    public ResponseEntity<ApiResponse<ClientResponse>> updateClientStatus(@PathVariable Long clientId, @RequestParam String status){
        return new ResponseEntity<>(clientService.updateClientStatus(clientId, status), HttpStatus.OK);
    }

    @GetMapping("/get-client/{clientId}")
    public ResponseEntity<ApiResponse<ClientResponse>> getClient(@PathVariable Long clientId){
        return new ResponseEntity<>(clientService.getByClientId(clientId), HttpStatus.OK);
    }

    @GetMapping("/getAll-client/{flag}")
    public ResponseEntity<ApiResponse<List<ClientResponse>>> getAllClient(@PathVariable int flag){
        return new ResponseEntity<>(clientService.getAllClient(flag), HttpStatus.OK);
    }
}
