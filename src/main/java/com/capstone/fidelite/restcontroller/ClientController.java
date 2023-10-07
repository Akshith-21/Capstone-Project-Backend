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
		System.out.println(client + "************");
		ResponseEntity<ClientDataTransferObject> response = null;
		ClientFMTS clientFmts = null;
		try {
			clientFmts = clientService.register(client);
			if(clientFmts == null) {
				response = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
			else {
				response = ResponseEntity.status(HttpStatus.OK).body(new ClientDataTransferObject(clientFmts.getClientId(), clientFmts.getToken()));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
		return response;
		
	}
	
	@GetMapping("/registers")
	ResponseEntity<Client> registerClient(){
		Person newPerson = new Person("ritiyuio@gmail.com", "7989", "28-11-2009", "India", "2097");
		Set<ClientIdentification> clientIdentificationSet = new HashSet<>();
		clientIdentificationSet.add(new ClientIdentification("pan", "F234"));
		Client newClient = new Client(newPerson,clientIdentificationSet);
		return ResponseEntity.status(HttpStatus.OK).body(newClient);
	}
	
	
	

}
