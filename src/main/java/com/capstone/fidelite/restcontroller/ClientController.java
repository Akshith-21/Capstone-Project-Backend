package com.capstone.fidelite.restcontroller;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import com.capstone.fidelite.services.ClientService;
import com.capstone.fidelite.models.Client;
import com.capstone.fidelite.models.ClientFMTS;
import com.capstone.fidelite.models.ClientIdentification;
import com.capstone.fidelite.models.Person;
@RestController
@RequestMapping("/client")
public class ClientController {
	@Autowired
	Logger logger;
	
	@Autowired
	ClientService clientService;
	
	@PostMapping("/register")
	 ResponseEntity<ClientDataTransferObject> registerClient(@RequestBody Client client) throws SQLException{
//		System.out.println(client + "************");
		ResponseEntity<ClientDataTransferObject> response = null;
		ClientFMTS clientFmts = null;
		try {
		clientFmts = clientService.register(client);
		if(clientFmts == null) {
			response = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();	
		}
		response = ResponseEntity.status(HttpStatus.OK).body(new ClientDataTransferObject(clientFmts.getClientId(), clientFmts.getToken()));
		}
		catch (Exception e) {
			e.printStackTrace();
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return response;	
	}
	
	@PostMapping("/login")
	ResponseEntity<String> clientLogin(@RequestBody LoginRequest loginRequest) throws SQLException{
		String email = loginRequest.getEmail();
		String pswd = loginRequest.getPswd();
		System.out.println(email+"controllerpswd");
		String clientId;
		ResponseEntity<String> response = null;
		try {
			clientId = clientService.login(email,pswd);
			if(clientId==null) {
				response =ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
			response = ResponseEntity.status(HttpStatus.OK).body(clientId);

		}
		catch (Exception e) {
			e.printStackTrace();
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return response;
	}

	
	

}
