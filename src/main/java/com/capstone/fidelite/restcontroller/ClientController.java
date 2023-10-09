package com.capstone.fidelite.restcontroller;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin("http://localhost:4200")
@RequestMapping("/client")

public class ClientController {
	@Autowired
	Logger logger;
	
	@Autowired
	ClientService clientService;
	
	@PostMapping("/register")
	 ResponseEntity<?> registerClient(@RequestBody Client client) throws SQLException{
//		System.out.println(client + "************");
		ResponseEntity<?> response = null;
		ClientFMTS clientFmts = null;
		try {
			
			clientFmts = clientService.register(client);
			if(clientFmts == null) {
				response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email or ClientIdentification already exist");
			}
			else {
				response = ResponseEntity.status(HttpStatus.OK).body(new ClientDataTransferObject(clientFmts.getClientId(), clientFmts.getToken()));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Data access error");
		}
		return response;	
	}

	@PostMapping("/login")
	ResponseEntity<?> clientLogin(@RequestBody LoginRequest loginRequest) throws SQLException{
		String email = loginRequest.getEmail();
		String pswd = loginRequest.getPswd();
		System.out.println(email+"controllerpswd");
		ClientFMTS clientFmts;
		ResponseEntity<?> response = null;
		try {
			clientFmts = clientService.login(email,pswd);
			if(clientFmts==null) {
				response =ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email Not Registered Please Sign Up");
			}
			response = ResponseEntity.status(HttpStatus.OK).body(new ClientDataTransferObject(clientFmts.getClientId(), clientFmts.getToken()));
		}
		catch (Exception e) {
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Data access error");
		}
		return response;
	}
	
	

}
